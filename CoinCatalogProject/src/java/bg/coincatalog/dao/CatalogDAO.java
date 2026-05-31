/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.dao;


import bg.coincatalog.model.CatalogItem;
import java.util.List;

public interface CatalogDAO {
List<CatalogItem> getAll() throws Exception;
List<CatalogItem> getByCountry(String country) throws Exception;
void insert(CatalogItem item) throws Exception;
void deleteById(int id) throws Exception;
int countAll() throws Exception;
int countByType(String type) throws Exception;
List<CatalogItem> search(String country, String type, String decade, String sort) throws Exception;
List<Integer> getDecades() throws Exception;
}
