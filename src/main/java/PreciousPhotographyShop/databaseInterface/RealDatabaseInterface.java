package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.databaseRepositories.PhotographRepository;
import PreciousPhotographyShop.databaseRepositories.UserRepository;
import PreciousPhotographyShop.model.Photograph;
import PreciousPhotographyShop.model.PhotographEntity;
import PreciousPhotographyShop.model.User;
import PreciousPhotographyShop.model.UserEntity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
