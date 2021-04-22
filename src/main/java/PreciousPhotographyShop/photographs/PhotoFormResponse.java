package PreciousPhotographyShop.photographs;

import java.util.LinkedList;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Matt
 */
public class PhotoFormResponse {
    private MultipartFile file;
    private List<String> categories = new LinkedList<>();
    private PhotographEntity containedEntity;
    
    public PhotoFormResponse(){
        containedEntity = new PhotographEntity();
    }
    
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
    
    public PhotographEntity getContainedEntity(){
        return containedEntity;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("PhotoFormResponse:\n");
        sb.append(String.format("\tFile: %s\n", file.getOriginalFilename()));
        sb.append("\tCategories: \n");
        categories.forEach((category)->{
            sb.append(String.format("\t\t%s\n", category));
        });
        sb.append(containedEntity.toString());
        return sb.toString();
    }
}
