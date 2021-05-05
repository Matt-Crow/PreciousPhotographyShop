package PreciousPhotographyShop.logging.logs;

import PreciousPhotographyShop.databaseInterface.LocalFileSystem;
import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Matt
 */
public abstract class AbstractLog {
    
    public final void logEvent(AbstractLoggedEvent event) throws IOException {
        File todaysFile = getFileForToday();
        createParentsIfAbsent(todaysFile);
        createFileIfAbsent(todaysFile);
        try(
            //                                                                  append
            BufferedWriter writer = new BufferedWriter(new FileWriter(todaysFile, true));
        ){
            writer.write(format(event.getMessageToLog()));
            writer.write('\n');
        } catch(IOException ex){
            throw ex;
        }
    };
    
    private File getFileForToday() throws IOException{
        String path = Paths.get(
            LocalFileSystem.getInstance().getLogFolder().getAbsolutePath(),
            getLogSubfolderName(),
            getLogFileName()
        ).toString();
        return new File(path);
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
     * @return the name of the subfolder of the log folder this log's files are
     * kept in
     */
    protected abstract String getLogSubfolderName();
    
    /**
     * 
     * @return the name of this log file for today. Some logs change files each
     * day to avoid getting too large
     */
    protected abstract String getLogFileName();
    
    /**
     * Only really used by WebsiteLog to encrypt
     * @param eventString
     * @return 
     */
    protected abstract String format(String eventString);
}
