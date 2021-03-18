package PreciousPhotographyShop.databaseRepositories;

import PreciousPhotographyShop.model.UserEntity;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    
}
