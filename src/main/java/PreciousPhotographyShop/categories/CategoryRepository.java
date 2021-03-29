package PreciousPhotographyShop.categories;

import org.springframework.data.repository.CrudRepository;

/**
 * can use this to get all available categories
 * @author Matt
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {
    public Iterable<CategoryEntity> getAllByParentName(String parentName); 
}
