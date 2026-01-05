/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.socket;

import bg.coincatalog.dao.CoinDAO;
import bg.coincatalog.model.Coin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class ClientHandler implements Runnable{
    
    private final Socket socket;
    
    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run(){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8")); 
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
          out.println("Welcome to CoinAdminServer. Commands: STATS, QUIT");
         
       String line;
       
       while((line=in.readLine())!=null){
           String raw = line.trim();
           String upper = raw.toUpperCase();
           
           if(upper.equals("QUIT")){
               out.println("BYE");
               break;
           }
             if (upper.equals("STATS")) {
                    CoinDAO dao = new CoinDAO();
                    int total = dao.countAll();
                    int coins = dao.countByType("COIN");
                    int banknotes = dao.countByType("BANKNOTE");

                    out.println("TOTAL=" + total);
                    out.println("COIN=" + coins);
                    out.println("BANKNOTE=" + banknotes);
                    out.println("END");
                }
             else if(upper.equals("EXPORT")){
                 CoinDAO dao = new CoinDAO();
                
                out.println("id,type,country,denomination,currency,coin_year,notes,image_front,image_back");

                 
                 for(bg.coincatalog.model.Coin c : dao.getAll()){
                    String notes = c.getNotes() == null ? "" : c.getNotes();
notes = notes.replace(",", " ").replace("\n", " ").replace("\r", " ");

                    String front = c.getImageFront() == null ? "" : c.getImageFront();
                    String back  = c.getImageBack()  == null ? "" : c.getImageBack();
                        out.println(
            c.getId() + "," +
            c.getType() + "," +
            c.getCountry() + "," +
            c.getDenomination() + "," +
            c.getCurrency() + "," +
            c.getCoinYear() + ","+
            notes + front + "," +
  back
        );
                 }
                 out.println("END");
             }
             else if(upper.startsWith("IMPORT")){
                 CoinDAO dao = new CoinDAO();
                 int imported = 0;
                 
                 String csvLine;
                 
                 csvLine = in.readLine();
                 
                 if(csvLine == null){
                     out.println("ERR No data");
                     continue;
                 }
            if(csvLine.toLowerCase().startsWith("id,type,country")){
                
            }else if(!csvLine.equals("END")){
                if(importOneLine(csvLine,dao)) imported++;
            }
            while((csvLine =in.readLine())!=null){
                 if(csvLine.equals("END")) break;
                 if(importOneLine(csvLine,dao)) imported++;
             }
            out.println("IMPORTED=" + imported);
            out.println("END");
             }
             else {
                    out.println(" ERR Unknown command");
                }
            }
       
      }catch(Exception ex){
       ex.printStackTrace();             
    }
        finally{
        try { socket.close(); } catch (Exception ignored) {}
}
    
}
private boolean importOneLine(String csvLine, CoinDAO dao) {
    try {
        if (csvLine == null) return false;

        
        if (csvLine.startsWith("\uFEFF")) csvLine = csvLine.substring(1);

      
        String delim = csvLine.contains(";") && !csvLine.contains(",") ? ";" : ",";
        String[] p = csvLine.split(java.util.regex.Pattern.quote(delim), -1);

       
        if (p.length < 7) return false; // минимално за данните
String imageFront = (p.length > 7) ? p[7].trim() : "";
String imageBack  = (p.length > 8) ? p[8].trim() : "";

        if (p[0].trim().equalsIgnoreCase("id")) return false;

        String type = p[1].trim();
        String country = p[2].trim();
        String denomination = p[3].trim();
        String currency = p[4].trim();
        int coinYear = Integer.parseInt(p[5].trim());
        String notes = p[6].trim();

   
        
        if (p.length >= 9) {
            if (imageFront.isEmpty()) imageFront = null;
            if (imageBack.isEmpty()) imageBack = null;
        }

        Coin c = new Coin();
        c.setType(type);
        c.setCountry(country);
        c.setDenomination(denomination);
        c.setCurrency(currency);
        c.setCoinYear(coinYear);
        c.setNotes(notes);
        c.setImageFront(imageFront);
        c.setImageBack(imageBack);

        dao.insert(c);
        return true;

    } catch (Exception ex) {
        return false;
    }
}

}
