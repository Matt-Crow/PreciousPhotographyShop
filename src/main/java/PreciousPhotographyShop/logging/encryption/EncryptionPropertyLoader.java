package PreciousPhotographyShop.logging.encryption;

import PreciousPhotographyShop.databaseInterface.LocalFileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 *
 * @author Matt
 */
public class EncryptionPropertyLoader {
    private static final String path = Paths.get(LocalFileSystem.MAIN_PATH, "config", ".enc.properties").toString();
    
    public final boolean exists(){
        File f = new File(path);
        return f.exists() && f.isFile();
    }
    
    public final void save(Properties props){
        File f = new File(path);
        if(!exists()){
            try {
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        try (
            FileOutputStream fout = new FileOutputStream(f);
        ) {
            props.store(fout, "Encryption properties for Precious Photography Shop");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public final Properties load(){
        Properties props = new Properties();
        
        File f = new File(path);
        if(f.exists()){
            try {
                props.load(new FileInputStream(f));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // need to create and store new properties
        }
        
        
        return props;
    }
}
