package PreciousPhotographyShop.index;

import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.categories.CategoryRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */
@Service
public class CategoryMatcher extends AbstractMatcher<CategoryEntity> {
    
    @Autowired private CategoryRepository catRepo;
    
    @Override
    protected Set<CategoryEntity> findMatches(String term) {
        Set<CategoryEntity> matches = new HashSet<>();
        catRepo.findAllByNameContainingIgnoreCase(term).forEach((cat)->{
            matches.add(cat);
        });
        return matches;
    }

}
