package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.photographs.PhotographRepository;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.Photograph;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.User;
import PreciousPhotographyShop.users.UserEntity;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
        // create user table record 
        UserEntity asEntity = new UserEntity();
        asEntity.setName(user.getName());
        asEntity.setEmail(user.getEmail());
        asEntity = this.userRepository.save(asEntity);
        user.setId(asEntity.getId()); // update user ID
        
        // create bridge table records
        user.getPhotos().forEach((Photograph photo)->{
            if(photo.getId() != null){
                UserToPhotographBridgeTableEntry entry = new UserToPhotographBridgeTableEntry();
                entry.setPhotographId(photo.getId());
                entry.setUserId(user.getId());
                this.userToPhotographBridgeTable.save(entry);
            }
        });
        
        return asEntity.getId();
    }

    @Override
    public User getUser(String id) {
        User u = null;
        UserEntity e = this.userRepository.findById(id).get();
        
        u = new User(
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

        User owner = photo.getOwner();
        if(owner.getId() != null){
            UserToPhotographBridgeTableEntry entry = new UserToPhotographBridgeTableEntry();
            entry.setPhotographId(withId.getId());
            entry.setUserId(owner.getId());
            this.userToPhotographBridgeTable.save(entry);
        }
        
        return withId.getId();
    }
    
    private Photograph tryConvert(PhotographEntity asEntity){
        Photograph ret = null;
        try {
            BufferedImage img = LocalFileSystem.getInstance().load(asEntity.getId());
            Iterator<PhotographToCategoryTableEntry> bte = this.photoToCategoryBridgeTable.findAllByPhotographId(asEntity.getId()).iterator();
            Set<String> catNames = new HashSet<>();
            while(bte.hasNext()){
                catNames.add(bte.next().getCategoryId());
            }
            UserToPhotographBridgeTableEntry ownership = this.userToPhotographBridgeTable.findByPhotographId(asEntity.getId()).orElse(null);
            ret = new Photograph(
                (ownership == null) ? null : getUser(ownership.getUserId()),
                asEntity.getName(),
                img,
                asEntity.getDescription(),
                asEntity.getPrice(),
                catNames,
                asEntity.getIsRecurring()
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
    
    /**
     * Gets all PhotographToCategoryTableEntrys with a category that is (or is
     * the descendant of) the given category.
     * 
     * @param topmost the topmost category to search under
     * 
     * @return all the PhotographToCategoryTableEntrys as described above
     */
    private Set<PhotographToCategoryTableEntry> getPhotoToCatBySupercategory(String topmost){
        HashSet<PhotographToCategoryTableEntry> ret = new HashSet<>();
        this.photoToCategoryBridgeTable.findAllByCategoryId(topmost).forEach(ret::add);
        // recursively call on each direct child category
        this.categoryRepository.findAllByParentName(topmost).forEach((catEnt)->{
            ret.addAll(this.getPhotoToCatBySupercategory(catEnt.getName()));
        });
        return ret;
    }
    
    @Override
    public Set<Photograph> getPhotographsByCategory(String category) {
        HashSet<Photograph> ret = new HashSet<>();
        Photograph curr = null;
        
        Iterable<PhotographToCategoryTableEntry> photosInCat = (category != null) 
            ? this.getPhotoToCatBySupercategory(category)
            : this.photoToCategoryBridgeTable.findAll();
        
        Iterator<PhotographToCategoryTableEntry> iter = photosInCat.iterator();
        while(iter.hasNext()){
            PhotographEntity pe = this.photographRepository.findById(iter.next().getPhotographId()).orElse(null);
            curr = this.tryConvert(pe);
            if(curr != null){
                ret.add(curr);
            }
        }
        
        return ret;
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
    public Set<String> getAllCategories() {
        Set<String> catNames = new HashSet<>();
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
            this.userToPhotographBridgeTable.deleteAllByPhotographId(id);
            this.photoToCategoryBridgeTable.deleteAllByPhotographId(id);
            // delete photo table record after bridge table entries
            this.photographRepository.deleteById(id);
        }
        return found;
    }

    @Override
    public int updatePhotoByID(String id, Photograph photograph) {
        int found = 0;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
