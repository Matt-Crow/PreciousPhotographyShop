package PreciousPhotographyShop.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Matt
 */

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findUserByEmail(String email);
}
