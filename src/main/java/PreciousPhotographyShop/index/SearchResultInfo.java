package PreciousPhotographyShop.index;

/**
 * Information about a single search result.
 * 
 * @author Matt Crow
 */
class SearchResultInfo {
    private final String text;
    private final String url;
    
    SearchResultInfo(String text, String url){
        this.text = text;
        this.url = url;
    }
    
    public final String getText(){
        return text;
    }
    
    public final String getUrl(){
        return url;
    }
    
    @Override
    public String toString(){
        return String.format("<a href=%s>%s</a>", url, text);
    }
}
