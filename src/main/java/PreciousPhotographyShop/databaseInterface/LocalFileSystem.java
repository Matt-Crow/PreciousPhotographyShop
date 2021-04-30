package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.PhotographEntity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 * 
 * @author Matt
 */
public class LocalFileSystem {
    private final String photoPath;
    
    private static LocalFileSystem instance;
    
    private LocalFileSystem(){
        photoPath = Paths.get(System.getProperty("user.home"), ".preciousPhotographShop").toString();
        getPhotoFolder(); // create folder if it does not yet exist
    }
    
    public static final LocalFileSystem getInstance(){
        if(instance == null){
            instance = new LocalFileSystem();
        }
        return instance;
    }
    
    private File getPhotoFolder(){
        File f = new File(this.photoPath);
        if(!f.exists()){
            f.mkdirs();
        }
        return f;
    }
    
    private String idToFilePath(String id){
        return Paths.get(this.photoPath, String.format("%s.jpg", id)).toString();
    }
    
    void store(PhotographEntity photo) throws IOException{
        File newFile = new File(idToFilePath(photo.getId()));
        ImageIO.write(photo.getPhoto(), "jpg", newFile);
    }
    
    final void delete(String id) throws IOException {
        File itsFile = new File(idToFilePath(id));
        itsFile.delete();
    }
    
    public final BufferedImage load(String id, boolean withWatermark) throws IOException{
        BufferedImage orig = ImageIO.read(new File(idToFilePath(id)));
        if(withWatermark){
            Graphics vandalizeMe = orig.createGraphics();
            vandalizeMe.setColor(Color.PINK);
            vandalizeMe.drawString("Precious Photography Shop", orig.getWidth() / 2, orig.getHeight() / 2);
        }
        return orig;
    }
}
