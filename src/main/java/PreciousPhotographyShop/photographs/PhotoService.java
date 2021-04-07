package PreciousPhotographyShop.photographs;


import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

//@Service
public class PhotoService {

    private final DatabaseInterface photoDB;

    @Autowired
    public PhotoService(@Qualifier("userDB") DatabaseInterface photoDB) {
        this.photoDB = photoDB;
    }

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




}
