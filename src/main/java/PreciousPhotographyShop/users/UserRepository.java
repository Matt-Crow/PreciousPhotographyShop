package PreciousPhotographyShop.users;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {
    
}
