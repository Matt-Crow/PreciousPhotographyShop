package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.categories.CategoryRepository;
import PreciousPhotographyShop.photographs.PhotographRepository;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
    
    @Override
    public String storeUser(UserEntity user) {
        // create / update user table record 
        user.setId(this.userRepository.save(user).getId());
        return user.getId();
    }

    /**
     * 
     * @param id the ID of the user to get
     * @return the user with the given ID. If none is found, throws an exception
     */
    @Override
    public UserEntity getUser(String id) {
        return this.userRepository.findById(id).get();
    }
    
    @Override
    public String storePhotograph(PhotographEntity photo){
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
        Collection<CategoryEntity> catEnts = photo.getCategoryNames().stream().map((categoryName)->{
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
        UserEntity owner = null;
        if(photo.getOwnerId() != null){
            owner = getUser(photo.getOwnerId());
        }
        if(owner != null && owner.getId() != null){
            owner.getPhotoIds().add(withId.getId());
            owner.setId(storeUser(owner)); // may have infinite recursion. Not sure
        }
        
        return withId.getId();
    }
    
    private PhotographEntity tryConvert(PhotographEntity asEntity, boolean withWatermark){
        PhotographEntity ret = null;
        
        
        try {
            BufferedImage img = LocalFileSystem.getInstance().load(asEntity.getId(), withWatermark);
            asEntity.setPhoto(img);
            ret = asEntity;
            /*
            ret = new Photograph(
                (asEntity.getOwnerId() == null) ? null : getUser(asEntity.getOwnerId()),
                asEntity.getName(),
                img,
                asEntity.getDescription(),
                asEntity.getPrice(),
                asEntity.getCategoryNames(),
                asEntity.getIsRecurring()
            );
            ret.setId(asEntity.getId());*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ret;
    }
    
    @Override 
    public PhotographEntity getPhotograph(String id, boolean withWatermark) {
        return tryConvert(photographRepository.findById(id).get(), withWatermark);
    }
    
    /**
     * Gets all PhotographToCategoryTableEntrys with a category that is (or is
     * the descendant of) the given category.
     * 
     * @param topmost the topmost category to search under
     * 
     * @return all the PhotographEntitys as described above
     */
    private Set<PhotographEntity> getPhotoBySupercategory(String topmost){
        HashSet<PhotographEntity> ret = new HashSet<>();
        this.photographRepository.findAllByCategoryNames(topmost).forEach(ret::add);
        // recursively call on each direct child category
        this.categoryRepository.findAllByParentName(topmost).forEach((catEnt)->{
            ret.addAll(this.getPhotoBySupercategory(catEnt.getName()));
        });
        return ret;
    }
    
    @Override
    public Set<PhotographEntity> getPhotographsByCategory(String category) {
        HashSet<PhotographEntity> ret = new HashSet<>();
        PhotographEntity curr = null;
        
        for(PhotographEntity pe : getPhotoBySupercategory(category)){
            curr = this.tryConvert(pe, true);
            if(curr != null){
                ret.add(curr);
            }
        };
        
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
    public HashMap<String, PhotographEntity> getAllPhotos() {
        HashMap<String, PhotographEntity> ret = new HashMap<>();
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
            // delete photo table record after bridge table entries
            this.photographRepository.deleteById(id);
        }
        return found;
    }

    @Override
    public int updatePhotoByID(String id, PhotographEntity photograph) {
        int found = 0;
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
