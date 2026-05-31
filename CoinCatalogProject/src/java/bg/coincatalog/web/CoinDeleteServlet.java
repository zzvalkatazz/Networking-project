/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.web;

import bg.coincatalog.dao.CoinDAO;
import bg.coincatalog.service.CoinService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="CoinDeleteServlet", urlPatterns={"/coin-delete"})
public class CoinDeleteServlet extends HttpServlet{
    @Override
     protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
      try{
         String idStr =request.getParameter("id");
         int id = Integer.parseInt(idStr);
         
         new CoinService().deleteItem(id);
         response.sendRedirect("coins");
     }catch(Exception ex){
         throw new ServletException("Delete error: " + ex.getMessage(), ex);
     }         
     }
}
