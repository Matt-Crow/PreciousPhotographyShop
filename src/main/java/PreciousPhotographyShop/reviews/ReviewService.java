package PreciousPhotographyShop.reviews;

import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import PreciousPhotographyShop.users.UserRepository;
import PreciousPhotographyShop.users.UserService;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class ReviewService {
    private final ReviewRepository reviews;
    private final UserRepository users;
    
    public ReviewService(ReviewRepository reviews, UserRepository users){
        this.reviews = reviews;
        this.users = users;
    }
    
    public final List<ReviewWidgetInfo> getReviewWidgetsFor(UserEntity seller){
        return mapReviewToWidget(getReviewsFor(seller));
    }
    
    public final List<ReviewWidgetInfo> getReviewWidgetsFor(PhotographEntity photo){
        return mapReviewToWidget(getReviewsFor(photo));
    }
    
    public final List<ReviewEntity> getReviewsFor(UserEntity seller){
        return getAsList(reviews.findAllByReviewedId(seller.getId()));
    }
    
    public final List<ReviewEntity> getReviewsFor(PhotographEntity photo){
        return getAsList(reviews.findAllByReviewedId(photo.getId()));
    }
    
    private List<ReviewEntity> getAsList(Iterable<ReviewEntity> iter){
        List<ReviewEntity> asList = new LinkedList<>();
        for(ReviewEntity review : iter){
            asList.add(review);
        }
        return asList;
    }
    
    private List<ReviewWidgetInfo> mapReviewToWidget(List<ReviewEntity> reviews){
        return reviews.stream().map((reviewEntity)->{
            ReviewWidgetInfo ret = null;
            try {
                ret = new ReviewWidgetInfo(
                    users.findById(reviewEntity.getReviewerId()).get().getUsername(),
                    reviewEntity.getText(),
                    reviewEntity.getRating()
                );
            } catch(Exception ex){
                ex.printStackTrace();
            }
            return ret;
        }).filter((wid)->wid != null).collect(Collectors.toList());
    }
}
