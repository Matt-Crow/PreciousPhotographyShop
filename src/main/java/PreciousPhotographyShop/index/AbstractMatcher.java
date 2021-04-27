package PreciousPhotographyShop.index;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Matt
 * @param <Matchable> the type of object this will match
 */
public abstract class AbstractMatcher<Matchable> {
    
    protected abstract Set<Matchable> findMatches(String term);
    
    /**
     * Finds a subset of the objects contained in this' pool such that each
     * object belongs to the intersection of each term.
     * 
     * For example, given a pool of {"abc", "abb", "acc"}, and terms {"a", "c"},
     * this will return {"abc", "acc"}. "abb" matches the first term, but not
     * the second.
     * 
     * @param terms
     * @return 
     */
    public final Set<Matchable> findMatches(String[] terms){
        Set<Matchable> matches = new HashSet<>();
        
        if(terms.length >= 1){
            // don't need to AND with anything: just return the set
            matches.addAll(findMatches(terms[0]));
        }
        
        for(int i = 1; i < terms.length; i++){
            // AND with all subsets matching terms after the first one
            matches.retainAll(findMatches(terms[i]));
        }
        
        return matches;
    }
}
