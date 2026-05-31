/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.service;

import bg.coincatalog.dao.CatalogDAO;
import bg.coincatalog.dao.CoinDAO;
import bg.coincatalog.exception.ValidationException;
import bg.coincatalog.model.CatalogItem;
import java.util.List;

public class CoinService {

private final CatalogDAO dao = new CoinDAO();

public void saveItem(CatalogItem item) throws Exception{
    
    if(item.getCoinYear() <0 || item.getCoinYear() > 2026){
        throw new ValidationException("Годината е невалидна. Трябва да е между 0 и 2026.");
    }
    if (item.getCountry() == null || item.getCountry().trim().isEmpty()) {
            throw new ValidationException("Полето за държава е задължително.");
        }
        if (item.getDenomination() == null || item.getDenomination().trim().isEmpty()) {
            throw new ValidationException("Полето за номинал е задължително.");
        }

        dao.insert(item);
}

public List<CatalogItem> searchItems(String country, String type, String decade, String sort) throws Exception {
        return dao.search(country, type, decade, sort);
    }

public void deleteItem(int id) throws Exception{
    dao.deleteById(id);
}

public List<Integer> getAvailableDecades() throws Exception{
    return dao.getDecades();
}

public List<CatalogItem> getAllItems() throws Exception{
    return dao.getAll();
}

public int getTotalCount() throws Exception{
    return dao.countAll();
}

public int getCountByType(String type) throws Exception{
    return dao.countByType(type);
}
}
