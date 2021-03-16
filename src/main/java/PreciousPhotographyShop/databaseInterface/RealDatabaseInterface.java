package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.databaseRepositories.CategoryRepository;
import PreciousPhotographyShop.databaseRepositories.PhotographRepository;
import PreciousPhotographyShop.databaseRepositories.UserRepository;
import PreciousPhotographyShop.model.CategoryEntity;
import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.PhotographEntity;
import PreciousPhotographyShop.model.User;
import PreciousPhotographyShop.model.UserEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    @Autowired // Autowiring is causing issues, null b/c using new keyword
    UserRepository userRepository;
    
    @Autowired
    PhotographRepository photographRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
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
        asEntity.setId(user.getId());
        asEntity.setName(user.getName());
        asEntity.setEmail(user.getEmail());
        this.userRepository.save(asEntity);
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

    // untested, and I doubt it works. Probably just save bufferedimage in photo
    @Override
    public void storePhotograph(Photograph photo){
        PhotographEntity pe = new PhotographEntity();
        //pe.setId(photo.getId());
        
        /*
        Find entities for the categories this photo belongs to
        */
        Collection<CategoryEntity> catEnts = photo.getCategories().stream().map((categoryName)->{
            // todo categoryName formatting
            CategoryEntity catEnt = this.categoryRepository.findById(categoryName).orElse(null);
            if(catEnt == null){
                catEnt = new CategoryEntity();
                catEnt.setName(categoryName);
            }
            return catEnt;
        }).collect(Collectors.toList());
        pe.setCategories(catEnts);
        
        /*
        Create new file for the photo
        */
        File root = this.getPhotoFolder();
        File newFile = Paths.get(root.getAbsolutePath(), photo.getId()).toFile();
        try {
            ImageIO.write(photo.getPhoto(), "jpg", newFile);
            this.photographRepository.save(pe);
        } catch (IOException ex) { 
            ex.printStackTrace();
        }
    }

    @Override // image data is under FILE_SYS_PHOTO_REPO/id
    public Photograph getPhotograph(String id, boolean withWatermark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
