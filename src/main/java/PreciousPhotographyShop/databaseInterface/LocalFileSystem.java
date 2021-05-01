package PreciousPhotographyShop.databaseInterface;

import PreciousPhotographyShop.photographs.PhotographEntity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 * 
 * @author Matt
 */
public class LocalFileSystem {
    private final String photoPath;
    private final String logPath;
    
    private static LocalFileSystem instance;
    
    private LocalFileSystem() throws IOException{
        String preciousFolder = Paths.get(System.getProperty("user.home"), ".preciousPhotographShop").toString();
        photoPath = preciousFolder;
        logPath = Paths.get(preciousFolder, "logs").toString();
        getPhotoFolder(); // create folder if it does not yet exist
        getLogFolder();
    }
    
    public static final LocalFileSystem getInstance() throws IOException{
        if(instance == null){
            instance = new LocalFileSystem();
        }
        return instance;
    }
    
    public final File getPhotoFolder(){
        File f = new File(this.photoPath);
        if(!f.exists()){
            f.mkdirs();
        }
        return f;
    }
    
    public final File getLogFolder() throws IOException{
        File f = new File(this.logPath);
        if(!f.exists()){
            Files.createDirectories(f.toPath());
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
