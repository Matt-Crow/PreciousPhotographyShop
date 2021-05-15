package PreciousPhotographyShop.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 * @author Matt
 */

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findUserByEmail(String email);
    Iterable<UserEntity> findAllByUsernameContainingIgnoreCase(String username);

    UserEntity findUserEntityByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity a SET a.enabled = TRUE WHERE a.email = ?1")
    int enableUser(String email);
}
