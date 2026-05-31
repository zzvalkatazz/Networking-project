/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.model;


public abstract class CatalogItem {
protected int id;
protected String type;
protected String country;
protected String denomination;
protected String currency;
protected int coinYear;
protected String notes;

protected String imageFront;
protected String imageBack;


public CatalogItem() {}


    public CatalogItem(int id, String type, String country, String denomination, String currency, int coinYear, String notes, String imageFront, String imageBack) {
        this.id = id;
        this.type = type;
        this.country = country;
        this.denomination = denomination;
        this.currency = currency;
        this.coinYear = coinYear;
        this.notes = notes;
        this.imageFront = imageFront;
        this.imageBack = imageBack;
    }
    
public abstract String getType();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public void setType(String type) { this.type = type; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDenomination() { return denomination; }
    public void setDenomination(String denomination) { this.denomination = denomination; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public int getCoinYear() { return coinYear; }
    public void setCoinYear(int coinYear) { this.coinYear = coinYear; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getImageFront() { return imageFront; }
    public void setImageFront(String imageFront) { this.imageFront = imageFront; }

    public String getImageBack() { return imageBack; }
    public void setImageBack(String imageBack) { this.imageBack = imageBack; }
}
