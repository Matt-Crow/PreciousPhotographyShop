package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.photographs.PhotographRepository;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.User;
import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserWithPhotos;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */

@Service // "Yo! Spring! This class can be used when I Autowire a DatabaseInterface!"
public class RealDatabaseInterface implements DatabaseInterface {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PhotographRepository photographRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    PhotoToCategoryBridgeTable photoToCategoryBridgeTable;
    
    @Autowired
    UserToPhotographBridgeTable userToPhotographBridgeTable;
    
    // move to LocalFileSystem class later?
    private static final String FILE_SYS_PHOTO_REPO = Paths.get(System.getProperty("user.home"), ".preciousPhotographShop").toString();
    
    private File getPhotoFolder(){
        File f = new File(FILE_SYS_PHOTO_REPO);
        if(!f.exists()){
            f.mkdirs();
        }
        return f;
    }
    
    @Override
    public String storeUser(User user) {
        UserEntity asEntity = new UserEntity();
        asEntity.setName(user.getName());
        asEntity.setEmail(user.getEmail());
        asEntity = this.userRepository.save(asEntity);
        user.setId(asEntity.getId()); // update user ID
        
        if(user instanceof UserWithPhotos){
            ((UserWithPhotos)user).getPhotos().forEach((Photograph photo)->{
                if(photo.getId() != null){
                    UserToPhotographBridgeTableEntry entry = new UserToPhotographBridgeTableEntry();
                    entry.setPhotographId(photo.getId());
                    entry.setUserId(user.getId());
                    this.userToPhotographBridgeTable.save(entry);
                }
            });
        }
        
        
        return asEntity.getId();
    }

    @Override
    public User getUser(String id) {
        UserWithPhotos u = null;
        UserEntity e = this.userRepository.findById(id).get();
        
        u = new UserWithPhotos(
            e.getName(),
            e.getEmail()
        );
        u.setId(e.getId());
        
        Iterator<UserToPhotographBridgeTableEntry> ownerships = this.userToPhotographBridgeTable.findAllByUserId(e.getId()).iterator();
        Photograph photo = null;
        while(ownerships.hasNext()){
            try {
                photo = this.getPhotograph(ownerships.next().getPhotographId(), true);
                u.addPhotograph(photo);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        return u;
    }
    
    @Override
    public String storePhotograph(Photograph photo){
        PhotographEntity pe = new PhotographEntity();
        pe.setName(photo.getName());
        
        /*
        Create new file for the photo
        */
        File root = this.getPhotoFolder();
        PhotographEntity withId = this.photographRepository.save(pe); // save() returns the changed pe
        try { 
            photo.setId(withId.getId());
            File newFile = Paths.get(root.getAbsolutePath(), withId.getId() + ".jpg").toFile();
            ImageIO.write(photo.getPhoto(), "jpg", newFile);
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
        
        /*
        Find entities for the categories this photo belongs to
        */
        Collection<CategoryEntity> catEnts = photo.getCategories().stream().map((categoryName)->{
            // todo categoryName formatting
            CategoryEntity catEnt = this.categoryRepository.findById(categoryName).orElse(null);
            if(catEnt == null){
                catEnt = new CategoryEntity();
                catEnt.setName(categoryName);
                categoryRepository.save(catEnt);
            }
            return catEnt;
        }).collect(Collectors.toList());
        
        /*
        Create bridge table entries 
        */
        catEnts.stream().forEach((catEnt)->{
            PhotographToCategoryTableEntry bte = new PhotographToCategoryTableEntry();
            bte.setCategoryId(catEnt.getName());
            bte.setPhotographId(pe.getId());
            this.photoToCategoryBridgeTable.save(bte);
        });        
        
        return withId.getId();
    }
    
    // image data is under FILE_SYS_PHOTO_REPO/id
    private Photograph tryConvert(PhotographEntity asEntity){
        Photograph ret = null;
        try {
            BufferedImage img = ImageIO.read(Paths.get(FILE_SYS_PHOTO_REPO, asEntity.getId() + ".jpg").toFile());
            Iterator<PhotographToCategoryTableEntry> bte = this.photoToCategoryBridgeTable.findAllByPhotographId(asEntity.getId()).iterator();
            List<String> catNames = new LinkedList<>();
            while(bte.hasNext()){
                catNames.add(bte.next().getCategoryId());
            }
            ret = new Photograph(
                asEntity.getName(),
                img,
                catNames.toArray(new String[catNames.size()])
            );
            ret.setId(asEntity.getId());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
    
    @Override 
    public Photograph getPhotograph(String id, boolean withWatermark) {
        Photograph photo = tryConvert(photographRepository.findById(id).get());
        
        System.err.println("Todo: add watermarking"); // maybe in tryConvert
        
        return photo;
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        // we'll have to decide how exactly this works.
        // for now, I'll treat it as "get all photographs belonging to at least one of these categories"
        // no categories = get all
        HashSet<Photograph> ret = new HashSet<>();
        Photograph curr = null;
        
        Iterable<PhotographToCategoryTableEntry> photosInCat = (categories.length != 0) 
            ? this.photoToCategoryBridgeTable.findAllByCategoryId(categories[0])
            : this.photoToCategoryBridgeTable.findAll();
        
        Iterator<PhotographToCategoryTableEntry> iter = photosInCat.iterator();
        while(iter.hasNext()){
            PhotographEntity pe = this.photographRepository.findById(iter.next().getPhotographId()).orElse(null);
            curr = this.tryConvert(pe);
            if(curr != null){
                ret.add(curr);
            }
        }
        
        return ret.toArray(new Photograph[ret.size()]);
    }
    
    //temp
    @Override
    public final List<String> getAllPhotoIds(){
        List<String> ret = new LinkedList<>();
        Iterator<PhotographEntity> iter = this.photographRepository.findAll().iterator();
        while(iter.hasNext()){
            ret.add(iter.next().getId());
        }
        return ret;
    }

    @Override
    public List<String> getAllCategories() {
        Iterator<CategoryEntity> cats = this.categoryRepository.findAll().iterator();
        LinkedList<String> catNames = new LinkedList<>();
        while(cats.hasNext()){
            catNames.add(cats.next().getName());
        }
        return catNames;
    }

    @Override
    public HashMap<String, Photograph> getAllPhotos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int deletePhotoByID(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updatePhotoByID(String id, Photograph photograph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
