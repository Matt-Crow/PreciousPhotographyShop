package PreciousPhotographyShop.model;

import PreciousPhotographyShop.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.awt.Image;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/photoController")
@RestController
public class PhotoController
{

    private final PhotoService photoService;

    @Autowired
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public void addPhoto(Photograph photograph){
        photoService.addPhoto(photograph);
    }

    // Going to need to handle a null pointer exception later
    @GetMapping(path = "{id}")
    public Photograph getPhotoByID(@PathVariable("id") UUID id){
        return photoService.getPhotoByID(id);
    }

    @DeleteMapping(path="{id}")
    public void deletePhotoByID(@PathVariable("id") UUID id){
        photoService.deletePhotograph(id);
    }

    @PutMapping(path="{id}")
    public void updatePhoto(@PathVariable("id") UUID id, @RequestBody Photograph photoToUpdate){
        photoService.updatePhotograph(id, photoToUpdate);
    }







}
