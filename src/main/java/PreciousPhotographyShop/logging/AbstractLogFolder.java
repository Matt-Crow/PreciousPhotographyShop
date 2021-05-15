package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.databaseInterface.LocalFileSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Matt
 * @param <LogType> the type of AbstractLog this folder will contain
 */
public abstract class AbstractLogFolder<LogType extends AbstractLog> {
    
    public final File getFolder() throws IOException{
        return Paths.get(
            LocalFileSystem.getInstance().getLogFolder().getAbsolutePath(),
            getLogSubfolderName()
        ).toFile();
    }
    
    /**
     * Gets or creates a file within this folder.
     * @param name the name of the file in this folder
     * @return
     * @throws IOException 
     */
    protected final File getOrCreateFile(String name) throws IOException{
        File f = Paths.get(
            getFolder().getAbsolutePath(),
            name
        ).toFile();
        
        if(!f.exists()){
            this.createParentsIfAbsent(f);
            this.createFileIfAbsent(f);
        }
        
        return f;
    }
    
    private void createParentsIfAbsent(File f) throws IOException{
        File parent = f.getParentFile();
        if(parent != null){
            Files.createDirectories(parent.toPath());
        }
    }
    
    private void createFileIfAbsent(File f) throws IOException{
        if(!f.exists()){
            Files.createFile(f.toPath());
        }
    }
    
    /**
     * 
     * @return the name of the subfolder of this log folder
     */
    protected abstract String getLogSubfolderName();
    
    public abstract LogType getLogByName(String name) throws IOException;    
}
