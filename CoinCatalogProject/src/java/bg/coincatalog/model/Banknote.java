/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.model;

public class Banknote extends CatalogItem{
    
    public Banknote(){
        super();
    }
    
    @Override
    public String getType(){
        return "BANKNOTE";
    }

}
