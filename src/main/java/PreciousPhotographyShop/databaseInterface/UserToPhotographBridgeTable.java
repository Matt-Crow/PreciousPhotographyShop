package PreciousPhotographyShop.databaseInterface;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Matt
 */
public interface UserToPhotographBridgeTable extends CrudRepository<UserToPhotographBridgeTableEntry, String> {
    public Iterable<UserToPhotographBridgeTableEntry> findAllByUserId(String userId);
    public Optional<UserToPhotographBridgeTableEntry> findByPhotographId(String photographId);
    public void deleteAllByPhotographId(String photographId);
}
