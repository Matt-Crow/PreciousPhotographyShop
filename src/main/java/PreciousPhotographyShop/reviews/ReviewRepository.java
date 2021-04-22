package PreciousPhotographyShop.reviews;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface ReviewRepository extends CrudRepository<ReviewEntity, String> {
    public Iterable<ReviewEntity> findAllByReviewerId(String reviewerId);
    public Iterable<ReviewEntity> findAllByReviewedId(String reviewedId);
}
