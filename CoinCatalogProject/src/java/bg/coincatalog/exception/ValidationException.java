/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.exception;


public class ValidationException extends CatalogException{
    public ValidationException(String message){
        super("Грешка при валидация:" + message);
    }
}
