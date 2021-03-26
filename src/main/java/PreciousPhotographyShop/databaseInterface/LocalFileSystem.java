package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.Photograph;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 * 
 * @author Matt
 */
class LocalFileSystem {
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
    
    void store(Photograph photo) throws IOException{
        File newFile = new File(idToFilePath(photo.getId()));
        ImageIO.write(photo.getPhoto(), "jpg", newFile);
    }
    
    BufferedImage load(String id) throws IOException{
        return ImageIO.read(new File(idToFilePath(id)));
    }
}
