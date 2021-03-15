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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Matt
 */
public class RealDatabaseInterface implements DatabaseInterface {
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PhotographRepository photographRepository;
    
    @Autowired
    CategoryRepository categoryRepository;
    
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
        pe.setId(photo.getId());
        
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
        
        
        //https://www.tutorialspoint.com/How-to-convert-Image-to-Byte-Array-in-java
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        BufferedImage buffy = new BufferedImage(photo.getPhoto().getWidth(null), photo.getPhoto().getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffy.createGraphics();
        g2d.drawImage(photo.getPhoto(), 0, 0, null);
        try {
            ImageIO.write(buffy, "jpg", bout);
            pe.setPhoto(bout.toByteArray());
            this.photographRepository.save(pe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        this.photographRepository.save(pe);
    }

    @Override
    public Photograph getPhotograph(String id, boolean withWatermark) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Photograph[] getPhotographsByCategory(String[] categories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}