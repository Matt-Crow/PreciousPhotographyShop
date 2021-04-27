package PreciousPhotographyShop.index;

import PreciousPhotographyShop.categories.CategoryEntity;
import PreciousPhotographyShop.photographs.PhotographEntity;
import PreciousPhotographyShop.users.UserEntity;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Matt
 */

@Service
public class SearchService {
    
    @Autowired private CategoryMatcher catMat;
    @Autowired private PhotographMatcher phoMat;
    @Autowired private UserMatcher userMat;
    
    public final Set<CategoryEntity> getMatchingCategories(String[] searchTerms){
        return catMat.findMatches(searchTerms);
    }
    
    public final Set<PhotographEntity> getMatchingPhotos(String[] searchTerms){
        return phoMat.findMatches(searchTerms);
    }
    
    public final Set<UserEntity> getMatchingUsers(String[] terms){
        return userMat.findMatches(terms);
    }
}
