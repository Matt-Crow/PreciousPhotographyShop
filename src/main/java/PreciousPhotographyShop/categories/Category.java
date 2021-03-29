package PreciousPhotographyShop.categories;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is the data class for Categories - how they are represented within the
 * program, rather than in the database.
 * 
 * @author Matt Crow
 */
public class Category {
    private final String name;
    private final Category parent;
    
    /*
    Doing it this way means once we retrieve one Category, all of them
    are retrieved. That is a problem.
    */
    private final Set<Category> children;
    
    public Category(String name, Category parent, Set<Category> children){
        this.name = name;
        this.parent = parent;
        this.children = children;
    }
    
    public Category(String name, Category parent){
        this(name, parent, new HashSet<>());
    }
    
    public Category(String name){
        this(name, null);
    }
    
    public final String getName(){
        return name;
    }
    
    public final boolean hasParentCategory(){
        return parent != null;
    }
    
    public final Category getParent(){
        return parent;
    }
    
    public final void addChild(Category subcategory){
        children.add(subcategory);
    }
    
    /**
     * 
     * @return a shallow copy of this' children 
     */
    public final Set<Category> getChildren(){
        // shallow copy
        return children.stream().collect(Collectors.toSet());
    }
    
    @Override
    public String toString(){
        return String.format("Category: %s", name);
    }
}
