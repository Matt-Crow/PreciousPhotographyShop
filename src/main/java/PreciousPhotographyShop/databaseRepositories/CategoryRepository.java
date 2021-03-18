package PreciousPhotographyShop.databaseRepositories;

import PreciousPhotographyShop.model.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * can use this to get all available categories
 * @author Matt
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {

}
