package PreciousPhotographyShop.databaseInterface;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface UserToPhotographBridgeTable extends CrudRepository<UserToPhotographBridgeTableEntry, String> {
    Iterable<UserToPhotographBridgeTableEntry> findAllByUserId(String userId);
    Optional<UserToPhotographBridgeTableEntry> findByPhotographId(String photographId);
}
