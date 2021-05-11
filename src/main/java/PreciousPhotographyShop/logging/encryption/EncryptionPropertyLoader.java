package PreciousPhotographyShop.logging.encryption;

import PreciousPhotographyShop.databaseInterface.LocalFileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Matt
 */
public class EncryptionPropertyLoader {
    private static final String path = Paths.get(LocalFileSystem.MAIN_PATH, "config", ".enc.properties").toString();
    
    private final String keyType;
    private final int bytesInKey;
    
    public EncryptionPropertyLoader(String keyType, int bytesInKey){
        this.keyType = keyType;
        this.bytesInKey = bytesInKey;
    }
    
    public final boolean exists(){
        File f = new File(path);
        return f.exists() && f.isFile();
    }
    
    public final void save(EncryptionProperties props) throws IOException{
        File f = new File(path);
        if(!exists()){
            f.getParentFile().mkdirs();
            f.createNewFile();
        }
        try (
            FileOutputStream fout = new FileOutputStream(f);
        ) {
            props.store(fout, "Encryption properties for Precious Photography Shop");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public final EncryptionProperties load(){
        EncryptionProperties props = new EncryptionProperties(keyType);
        
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
    
    public final EncryptionProperties createAndStore() throws NoSuchAlgorithmException, IOException{
        EncryptionKeyProvider propProv = new EncryptionKeyProvider(keyType, bytesInKey);
        EncryptionProperties props = new EncryptionProperties(keyType);
        props.setKey(propProv.newSecretKey());
        props.setIv(propProv.newIvParameter());
        save(props);
        return props;
    }
}
