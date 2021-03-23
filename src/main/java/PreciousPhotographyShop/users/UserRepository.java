package PreciousPhotographyShop.users;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 *
 * @author Matt
 */
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findUserByEmail(String email);
}
