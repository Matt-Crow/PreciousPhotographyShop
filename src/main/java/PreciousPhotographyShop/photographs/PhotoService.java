package PreciousPhotographyShop.photographs;


import PreciousPhotographyShop.databaseInterface.DatabaseInterface;
import PreciousPhotographyShop.photographs.Photograph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.UUID;

//@Service
public class PhotoService {

    private final DatabaseInterface photoDB;

    @Autowired
    public PhotoService(@Qualifier("userDB") DatabaseInterface photoDB) {
        this.photoDB = photoDB;
    }

    public void addPhoto(Photograph photograph){
        photoDB.storePhotograph(photograph);
    }

    // However Matt designs database
    @GetMapping
    public HashMap<UUID, Photograph> getAllPhotos(){
        return photoDB.getAllPhotos();
    }

    public Photograph getPhotoByID(UUID id){
        return photoDB.getPhotograph(id);
    }

    public int deletePhotograph(UUID id){
        return photoDB.deletePhotoByID(id);
    }

    public int updatePhotograph(UUID id, Photograph newPhoto){
        return photoDB.updatePhotoByID(id, newPhoto);
    }




}
