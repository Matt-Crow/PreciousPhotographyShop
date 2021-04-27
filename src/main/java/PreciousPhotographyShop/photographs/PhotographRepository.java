package PreciousPhotographyShop.photographs;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface PhotographRepository extends CrudRepository<PhotographEntity, String> {
    public Iterable<PhotographEntity> findAllByCategoryNames(String categoryName);
    public Iterable<PhotographEntity> findAllByNameContainingIgnoreCase(String name);
    public Iterable<PhotographEntity> findAllByDescriptionContainingIgnoreCase(String desc);
}
