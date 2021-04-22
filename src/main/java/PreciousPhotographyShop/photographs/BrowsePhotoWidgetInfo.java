package PreciousPhotographyShop.photographs;

/**
 * This class is used to pass data to the photograph widget used by browsing 
 * photographs, and defined in fragments.html
 * 
 * @author Matt Crow
 */
public class BrowsePhotoWidgetInfo {
    private final String photoId;
    private final String priceStr;
    private final String title;
    
    public BrowsePhotoWidgetInfo(String photoId, String priceStr, String title){
        this.photoId = photoId;
        this.priceStr = priceStr;
        this.title = title;
    }
    
    public final String getPhotoId(){
        return photoId;
    }
    
    public final String getPriceStr(){
        return priceStr;
    }
    
    public final String getTitle(){
        return title;
    }
}
