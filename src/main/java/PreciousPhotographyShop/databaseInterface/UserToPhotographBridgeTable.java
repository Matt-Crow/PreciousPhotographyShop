package PreciousPhotographyShop.databaseInterface;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface UserToPhotographBridgeTable extends CrudRepository<UserToPhotographBridgeTableEntry, String> {
    Iterable<UserToPhotographBridgeTableEntry> findAllByUserId(String userId);
    UserToPhotographBridgeTableEntry findByPhotographId(String photographId);
}
