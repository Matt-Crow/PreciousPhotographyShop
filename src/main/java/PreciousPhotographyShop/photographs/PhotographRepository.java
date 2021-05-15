package PreciousPhotographyShop.photographs;

import java.util.Iterator;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface PhotographRepository extends CrudRepository<PhotographEntity, String> {
    public Iterable<PhotographEntity> findAllByCategoryNames(String categoryName);
    public Iterable<PhotographEntity> findAllByNameContainingIgnoreCase(String name);
    public Iterable<PhotographEntity> findAllByDescriptionContainingIgnoreCase(String desc);
    public Iterator<PhotographEntity> findAllByOwnerId(String id);
    //public Iterator<PhotographEntity> findAllByOwnerIdOrderByPostedDateDesc(String id);
}
