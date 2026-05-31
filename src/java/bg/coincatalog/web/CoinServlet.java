/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.web;

import bg.coincatalog.dao.CoinDAO;
import bg.coincatalog.model.CatalogItem;
import bg.coincatalog.model.Coin;
import bg.coincatalog.service.CoinService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/coins")
public class CoinServlet extends HttpServlet{
    
    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
       
         
    request.setCharacterEncoding("UTF-8");   
    response.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    
           String country = trimToNull(request.getParameter("country"));
           String type = trimToNull(request.getParameter("type"));
           String decade = request.getParameter("decade");
           String sort = trimToNull(request.getParameter("sort"));
           if(sort == null) sort ="year_asc";
           if(!"year_asc".equals(sort) && !"year_desc".equals(sort)) sort="year_asc";
           try{
              CoinService service = new CoinService();   
              List<CatalogItem> coins = service.searchItems(country, type, decade, sort);
               request.setAttribute("coins", coins);
              request.setAttribute("country",country == null ? "" : country);
              request.setAttribute("type", type == null ? "" : type);
              request.setAttribute("sort", sort);
              List<Integer> decades = service.getAvailableDecades();
              request.setAttribute("decades", decades);
              request.getRequestDispatcher("/coins.jsp").forward(request, response);
              
       } catch (Exception ex) {
           throw new ServletException("DB error:" + ex.getMessage(),ex);
        }
     }
     private String trimToNull(String s){
         if(s==null) return null;
         s=s.trim();
         return s.isEmpty() ? null :s;
     }
}
