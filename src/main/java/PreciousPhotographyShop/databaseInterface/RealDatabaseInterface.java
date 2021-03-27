package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.photographs.PhotographRepository;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.OwnedPhotograph;
import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.User;
import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserWithPhotos;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */

@Service // "Yo! Spring! This class can be used when I Autowire a DatabaseInterface!"
public class RealDatabaseInterface implements DatabaseInterface {
    @Autowired UserRepository userRepository;
    @Autowired PhotographRepository photographRepository;
    @Autowired CategoryRepository categoryRepository;
    @Autowired PhotoToCategoryBridgeTable photoToCategoryBridgeTable;
    @Autowired UserToPhotographBridgeTable userToPhotographBridgeTable;
    
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
        PhotographEntity withId = this.photographRepository.save(pe); // save() returns the changed pe
        try { 
            photo.setId(withId.getId());
            LocalFileSystem.getInstance().store(photo);
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

        if(photo instanceof OwnedPhotograph){
            User owner = ((OwnedPhotograph)photo).getOwner();
            if(owner.getId() != null){
                UserToPhotographBridgeTableEntry entry = new UserToPhotographBridgeTableEntry();
                entry.setPhotographId(withId.getId());
                entry.setUserId(owner.getId());
                this.userToPhotographBridgeTable.save(entry);
            }
        }
        
        return withId.getId();
    }
    
    private Photograph tryConvert(PhotographEntity asEntity){
        Photograph ret = null;
        try {
            BufferedImage img = LocalFileSystem.getInstance().load(asEntity.getId());
            Iterator<PhotographToCategoryTableEntry> bte = this.photoToCategoryBridgeTable.findAllByPhotographId(asEntity.getId()).iterator();
            List<String> catNames = new LinkedList<>();
            while(bte.hasNext()){
                catNames.add(bte.next().getCategoryId());
            }
            String[] catNameArray = catNames.toArray(new String[catNames.size()]);
            UserToPhotographBridgeTableEntry ownership = this.userToPhotographBridgeTable.findByPhotographId(asEntity.getId()).orElse(null);
            if(ownership == null){
                ret = new Photograph(
                    asEntity.getName(),
                    img,
                    catNameArray
                );
            } else {
                ret = new OwnedPhotograph(
                    asEntity.getName(),
                    img,
                    catNameArray,
                    this.getUser(ownership.getUserId())
                );
            }
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
        this.photographRepository.findAll().forEach((PhotographEntity pe)->{
            ret.add(pe.getId());
        });
        return ret;
    }

    @Override
    public List<String> getAllCategories() {
        LinkedList<String> catNames = new LinkedList<>();
        this.categoryRepository.findAll().forEach((CategoryEntity catEnt)->{
            catNames.add(catEnt.getName());
        });
        return catNames;
    }

    @Override
    public HashMap<String, Photograph> getAllPhotos() {
        HashMap<String, Photograph> ret = new HashMap<>();
        this.photographRepository.findAll().forEach((photoEntity)->{
            ret.put(photoEntity.getId(), getPhotograph(photoEntity.getId(), true));
        });        
        return ret;
    }

    @Override
    public int deletePhotoByID(String id) {
        int found = 0;
        if(this.photographRepository.findById(id).orElse(null) != null){
            found = 1;
            this.photographRepository.deleteById(id);
            this.userToPhotographBridgeTable.deleteAllByPhotographId(id);
            this.photoToCategoryBridgeTable.deleteAllByPhotographId(id);
        }
        return found;
    }

    @Override
    public int updatePhotoByID(String id, Photograph photograph) {
        int found = 0;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
