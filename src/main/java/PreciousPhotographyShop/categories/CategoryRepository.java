package PreciousPhotographyShop.categories;

import org.springframework.data.repository.CrudRepository;

/**
 * can use this to get all available categories
 * @author Matt
 */
public interface CategoryRepository extends CrudRepository<CategoryEntity, String> {
    public Iterable<CategoryEntity> findAllByParentName(String parentName);
    public Iterable<CategoryEntity> findAllByNameContainingIgnoreCase(String name);
}
