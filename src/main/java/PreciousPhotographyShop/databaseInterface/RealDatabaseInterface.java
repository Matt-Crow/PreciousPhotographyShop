package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.databaseRepositories.CategoryRepository;
import PreciousPhotographyShop.databaseRepositories.PhotographRepository;
import PreciousPhotographyShop.databaseRepositories.UserRepository;
import PreciousPhotographyShop.model.CategoryEntity;
import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.PhotographEntity;
import PreciousPhotographyShop.model.User;
import PreciousPhotographyShop.model.UserEntity;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collection;
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
    public void storeUser(User user) {
        UserEntity asEntity = new UserEntity();
        asEntity.setName(user.getName());
        asEntity.setEmail(user.getEmail());
        asEntity = this.userRepository.save(asEntity);
        user.setId(asEntity.getId()); // update user ID
    }

    @Override
    public User getUser(String id) {
        User u = null;
        UserEntity e = this.userRepository.findById(id).get();
        // throws error if not found
        
        u = new User(
            e.getName(),
            e.getEmail(),
            e.getId()
        );
        
        return u;
    }
    
    @Override
    public void storePhotograph(Photograph photo){
        PhotographEntity pe = new PhotographEntity();
        pe.setName(photo.getName());
        
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
        pe.setCategories(catEnts);
        
        /*
        Create new file for the photo
        */
        File root = this.getPhotoFolder();
        
        try {            
            pe = this.photographRepository.save(pe); // save() returns the changed pe
            photo.setId(pe.getId());
            File newFile = Paths.get(root.getAbsolutePath(), pe.getId()).toFile();
            ImageIO.write(photo.getPhoto(), "jpg", newFile);
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }

    @Override // image data is under FILE_SYS_PHOTO_REPO/id
    public Photograph getPhotograph(String id, boolean withWatermark) {
        PhotographEntity asEntity = this.photographRepository.findById(id).get();
        BufferedImage img = null;
        try {
            img = ImageIO.read(Paths.get(FILE_SYS_PHOTO_REPO, id).toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Photograph photo = new Photograph(
            asEntity.getName(),
            img,
            asEntity.getId(),
            asEntity.getCategories().stream().map((cat)->cat.getName()).toArray((s)->new String[s])
        );
        
        System.err.println("Todo: add watermarking");
        
        return photo;
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
