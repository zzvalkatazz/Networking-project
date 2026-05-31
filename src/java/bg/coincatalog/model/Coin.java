package bg.coincatalog.model;

public class Coin extends CatalogItem {
   
    public Coin(){
        super();
    }

 
    
    @Override
    public String getType(){
        return "COIN";
    }
}