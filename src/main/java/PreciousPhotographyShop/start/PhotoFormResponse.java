package PreciousPhotographyShop.start;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Matt
 */
public class PhotoFormResponse {
    private MultipartFile file;
    private String name;
    private List<String> categories;
    
    public void setFile(MultipartFile file){
        this.file = file;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCategories(List<String> categories){
        this.categories = categories;
    }
    
    public MultipartFile getFile(){
        return file;
    }
    
    public String getName(){
        return name;
    }
    
    public List<String> getCategories(){
        return categories;
    }
}
