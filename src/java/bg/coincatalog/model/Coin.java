/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.model;


public class Coin {
private int id;
private String type;
private String country;
private String denomination;
private String currency;
private int coinYear;
private String notes;
private String imageUrlFront;
private String imageFileFront;
private String imageUrlBack;
private String imageFileBack;



public Coin(){}

public Coin(int id, String type, String country,String denomination, String currency, int coinYear, String notes,String imageUrlBack,String imageFileBack){
        this.id=id;
        this.type=type;
        this.country= country;
        this.denomination = denomination;
        this.currency = currency;
        this.coinYear = coinYear;
        this.notes = notes;
        this.imageUrlBack= imageUrlBack;
        this.imageFileBack=imageFileBack;
}
public int getId(){
    return id;
}
public void setId(int id){
    this.id=id;
}

public String getType(){
    return type;
}
public void setType(String type){
    this.type= type;
}

public String getCountry(){
    return country;
}
public void setCountry(String country){
    this.country=country;
}
public String getDenomination(){
    return denomination;
}
public void setDenomination(String denomination){
    this.denomination=denomination;
}
public String getCurrency(){
    return currency;
}
public void setCurrency(String currency){
    this.currency=currency;
}
public int getCoinYear(){
    return coinYear;
}
public void setCoinYear(int coinYear){
    this.coinYear=coinYear;
}
public String getNotes(){
    return notes;
}
public void setNotes(String notes){
   this.notes=notes;
}
private String imageFront;
private String imageBack;

public String getImageFront() { return imageFront; }
public void setImageFront(String imageFront) { this.imageFront = imageFront; }

public String getImageBack() { return imageBack; }
public void setImageBack(String imageBack) { this.imageBack = imageBack; }
}