package PreciousPhotographyShop.testsAndExamples;

import java.util.Collection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Matt
 */
public interface TestRepository extends CrudRepository<TestEntity, String>{
    @Query(
        value = "SELECT * from test_entity WHERE test_id IN "
        + "(SELECT test_id FROM test_entity_id_bridge WHERE related_ids = :relId)",
        nativeQuery = true // "this is an SQL statement"
    )
    public Collection<TestEntity> findAllByRelatedId(@Param("relId") String relatedId);
}
