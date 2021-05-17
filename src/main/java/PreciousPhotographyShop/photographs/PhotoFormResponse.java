package PreciousPhotographyShop.photographs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Matt
 */
public class PhotoFormResponse {
    private MultipartFile file;
    private String categories;
    private PhotographEntity containedEntity;
    
    public PhotoFormResponse(){
        containedEntity = new PhotographEntity();
    }
    
    public void setFile(MultipartFile file){
        this.file = file;
    }
    
    public void setCategories(String categories){
        this.categories = categories;
    }
    
    public MultipartFile getFile(){
        return file;
    }
    
    public String getCategories(){
        return categories;
    }
    
    public List<String> getCategoryList(){// split on whitespace
        return Arrays.stream(categories.split("\\s+")).collect(Collectors.toList());
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
        getCategoryList().forEach((category)->{
            sb.append(String.format("\t\t%s\n", category));
        });
        sb.append(containedEntity.toString());
        return sb.toString();
    }
}
