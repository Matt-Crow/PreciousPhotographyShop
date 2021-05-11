package PreciousPhotographyShop.logging;

import PreciousPhotographyShop.logging.events.AbstractLoggedEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matt
 */
public abstract class AbstractLog {
    private final String path;
    
    public AbstractLog(String path){
        this.path = path;
    }
    
    public final void logEvent(AbstractLoggedEvent event) throws IOException {
        File todaysFile = new File(path);
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
    
    public final String getText() throws IOException{
        File todaysFile = new File(this.path);
        String contents = "";
        if(todaysFile.exists()){
            contents = readContents(todaysFile);
        }
        return contents;
    }
    
    private String readContents(File f){
        StringBuilder sb = new StringBuilder();
        try (
            FileReader freed = new FileReader(f);
            BufferedReader buff = new BufferedReader(freed);
        ){
            while(buff.ready()){
                sb.append(buff.readLine()).append('\n');
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * Only really used by WebsiteLog to encrypt
     * @param eventString
     * @return 
     */
    protected abstract String format(String eventString);
}
