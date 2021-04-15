package PreciousPhotographyShop.reviews;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * A ReviewEntity represents a review for a seller or photograph
 * @author Matt
 */

@Entity
@Table(name="reviews")
public class ReviewEntity {
    @Id
    @Column(name="review_id")
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String reviewId;
    
    @Column(name="user_id", nullable=false)
    private String reviewerId; // who reviewed the item
    
    @Column(name="reviewed_id", nullable=false)
    private String reviewedId; // what or who was reviewed
    
    @Column(name="text")
    private String text = "no review text given";
    
    @Column(name="rating")
    private int rating; // 0 - 5
    
    public ReviewEntity(){
        // obligatory no-arg ctor
    }
    
    public ReviewEntity(String reviewerId, String reviewedId, String text, int rating){
        this();
        this.reviewerId = reviewerId;
        this.reviewedId = reviewedId;
        this.text = text;
        this.rating = rating;
    }
    
    public void setReviewId(String reviewId){
        this.reviewId = reviewId;
    }
    
    public void setReviewerId(String reviewerId){
        this.reviewerId = reviewerId;
    }
    
    public void setReviewedId(String reviewedId){
        this.reviewedId = reviewedId;
    }
    
    public void setText(String text){
        this.text = text;
    }
    
    public void setRating(int rating){
        this.rating = rating;
    }
    
    public String getReviewId(){
        return reviewId;
    }
    
    public String getReviewerId(){
        return reviewerId;
    }
    
    public String getReviewedId(){
        return reviewedId;
    }
    
    public String getText(){
        return text;
    }
    
    public int getRating(){
        return rating;
    }
    
    @Override
    public String toString(){
        return String.format("%s's review of %s:\n\t\"%s\" (%d / 5)", 
            this.reviewerId,
            this.reviewedId,
            this.text,
            this.rating
        );
    }
}
