package PreciousPhotographyShop.photographs;


import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import java.text.NumberFormat;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    
    @Autowired
    private DatabaseInterface photoDB;
    
    /*
    @Autowired
    public PhotoService(@Qualifier("userDB") DatabaseInterface photoDB) {
        this.photoDB = photoDB;
    }*/

    public void addPhoto(PhotographEntity photograph){
        photoDB.storePhotograph(photograph);
    }

    // However Matt designs database
    @GetMapping
    public HashMap<String, PhotographEntity> getAllPhotos(){
        return photoDB.getAllPhotos();
    }

    public PhotographEntity getPhotoByID(String id){
        return photoDB.getPhotograph(id, true);
    }

    public int deletePhotograph(String id){
        return photoDB.deletePhotoByID(id);
    }

    public int updatePhotograph(String id, PhotographEntity newPhoto){
        return photoDB.updatePhotoByID(id, newPhoto);
    }
    
    private BrowsePhotoWidgetInfo mapPhotoToBrowseWidget(PhotographEntity photo){
        String sellerName = "Unknown Seller";
            try {
                sellerName = photoDB.getUser(photo.getOwnerId()).getUsername();
            } catch(Exception ex){
                ex.printStackTrace();
            }
            String priceStr = NumberFormat.getCurrencyInstance().format(photo.getPrice());
            String title = String.format("%s - by %s", photo.getName(), sellerName);
            return new BrowsePhotoWidgetInfo(photo.getId(), priceStr, title);
    }
    public final Collection<BrowsePhotoWidgetInfo> getBrowseWidgetsForAllPhotos(){
        return getAllPhotos().values().stream().map(this::mapPhotoToBrowseWidget).collect(Collectors.toSet());
    }
    
    public final Collection<BrowsePhotoWidgetInfo> getBrowseWidgetsByCategory(String category){
        return photoDB.getPhotographsByCategory(category).stream().map(this::mapPhotoToBrowseWidget).collect(Collectors.toList());
    }
}
