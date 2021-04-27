package PreciousPhotographyShop.index;

import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.photographs.PhotographRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class PhotographMatcher extends AbstractMatcher<PhotographEntity> {
    
    @Autowired private PhotographRepository photoRepo;
    
    @Override
    protected Set<PhotographEntity> findMatches(String term) {
        Set<PhotographEntity> matches = new HashSet<>();
        photoRepo.findAllByNameContainingIgnoreCase(term).forEach((photo)->{
            matches.add(photo);
        });
        photoRepo.findAllByDescriptionContainingIgnoreCase(term).forEach((photo)->{
            matches.add(photo);
        });
        return matches;
    }

}
