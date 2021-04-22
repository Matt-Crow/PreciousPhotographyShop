package PreciousPhotographyShop.reviews;

/**
 * Used to forward information to the embedded review widget definded in
 * fragments.html
 * 
 * @author Matt
 */
public class ReviewWidgetInfo {
    private final String reviewerName;
    private final String reviewText;
    private final int rating;
    
    public ReviewWidgetInfo(String reviewerName, String reviewText, int rating){
        this.reviewerName = reviewerName;
        this.reviewText = reviewText;
        this.rating = rating;
    }
    
    public final String getReviewerName(){
        return reviewerName;
    }
    
    public final String getReviewText(){
        return reviewText;
    }
    
    public final int getRating(){
        return rating;
    }
}
