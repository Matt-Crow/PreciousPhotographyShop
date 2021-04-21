package PreciousPhotographyShop.photographs;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Matt
 */
public class PhotoFormResponse extends PhotographEntity {
    private MultipartFile file;
    private List<String> categories;
    
    public void setFile(MultipartFile file){
        this.file = file;
    }
    
    public void setCategories(List<String> categories){
        this.categories = categories;
    }
    
    public MultipartFile getFile(){
        return file;
    }
    
    public List<String> getCategories(){
        return categories;
    }
}
