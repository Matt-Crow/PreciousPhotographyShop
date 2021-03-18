package PreciousPhotographyShop.databaseRepositories;

import PreciousPhotographyShop.model.PhotographEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface PhotographRepository extends CrudRepository<PhotographEntity, String> {

}
