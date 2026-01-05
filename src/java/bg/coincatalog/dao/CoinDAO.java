    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package bg.coincatalog.dao;

    import bg.coincatalog.db.DB;
    import bg.coincatalog.model.Coin;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.util.ArrayList;
    import java.util.List;


    public class CoinDAO {

    public List<Coin> getAll() throws Exception{
        String sql = "SELECT ID, TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, " +
             "IMAGE_FRONT, IMAGE_BACK " +
             "FROM COINS ORDER BY ID";

        List<Coin> result = new ArrayList<>();

       try(Connection con = DB.getConnection();
           PreparedStatement ps =con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()){
               while(rs.next()){
                   Coin c = new Coin();
                    c.setId(rs.getInt("ID"));
                    c.setType(rs.getString("TYPE"));
                    c.setCountry(rs.getString("COUNTRY"));
                    c.setDenomination(rs.getString("DENOMINATION"));
                    c.setCurrency(rs.getString("CURRENCY"));
                    c.setCoinYear(rs.getInt("COIN_YEAR"));
                    c.setNotes(rs.getString("NOTES"));
                    c.setImageFront(rs.getString("IMAGE_FRONT"));
                    c.setImageBack(rs.getString("IMAGE_BACK"));


                    result.add(c);
               }
           }
       return result;
       }
    
    
    public List<Coin> getByCountry(String country) throws Exception{
       String sql = "SELECT ID, TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, " +
        "IMAGE_FRONT, IMAGE_BACK " +
        "FROM COINS WHERE COUNTRY = ? ORDER BY ID";
       
       List<Coin> result = new ArrayList<>();
       try(Connection con = DB.getConnection();
               PreparedStatement ps = con.prepareStatement(sql)){
               ps.setString(1, country);
               
               try(ResultSet rs = ps.executeQuery()){
                   while(rs.next()){
                       Coin c = new Coin();
                        c.setId(rs.getInt("ID"));
                    c.setType(rs.getString("TYPE"));
                    c.setCountry(rs.getString("COUNTRY"));
                    c.setDenomination(rs.getString("DENOMINATION"));
                    c.setCurrency(rs.getString("CURRENCY"));
                    c.setCoinYear(rs.getInt("COIN_YEAR"));
                    c.setNotes(rs.getString("NOTES"));
                    c.setImageFront(rs.getString("IMAGE_FRONT"));
                    c.setImageBack(rs.getString("IMAGE_BACK"));
                    result.add(c);
                   }
               }
       }
       return result;
    }
    public void insert(Coin c) throws Exception{
       String sql = "INSERT INTO COINS (TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, " +
             "IMAGE_FRONT, IMAGE_BACK) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        
        try(Connection con = DB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, c.getType());
            ps.setString(2, c.getCountry());
            ps.setString(3, c.getDenomination());
            ps.setString(4,c.getCurrency());
            ps.setInt(5,c.getCoinYear());
            ps.setString(6,c.getNotes());
            ps.setString(7, c.getImageFront()); 
            ps.setString(8, c.getImageBack());

            ps.executeUpdate();
        }
    }
  public void deleteById(int id) throws Exception{
      String sql = "DELETE FROM COINS WHERE ID = ?";
      
      try(Connection con =DB.getConnection();
              PreparedStatement ps =con.prepareStatement(sql)){
          ps.setInt(1, id);
          ps.executeUpdate();
      }
  }
 public int countAll() throws Exception{
     String sql = "SELECT COUNT(*) AS C FROM COINS";
     try(Connection con= DB.getConnection();
          PreparedStatement ps =con.prepareStatement(sql);
           ResultSet rs = ps.executeQuery()){
         rs.next();
         return rs.getInt(1);
     }
 }
 public int countByType(String type) throws Exception{
     String sql ="SELECT COUNT(*) AS C FROM COINS WHERE TYPE = ?";
     try(Connection con = DB.getConnection();
             PreparedStatement ps =con.prepareStatement(sql)){
              ps.setString(1, type);
              try(ResultSet rs = ps.executeQuery()){
                  rs.next();
                  return rs.getInt(1);
              }
     }
 }
 public List<Coin> search(String country, String type,String decade,String sort) throws Exception{
  StringBuilder sql = new StringBuilder();
    sql.append("SELECT ID, TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, ");
    sql.append("IMAGE_FRONT, IMAGE_BACK ");
    sql.append("FROM COINS ");
    sql.append("WHERE 1=1 ");
     
     List<Object> params = new java.util.ArrayList<>();
     if(country !=null){
         sql.append("ÄND COUNTRY = ? ");
         params.add(country.trim());
     }
     if(type !=null){
         sql.append(" AND TYPE = ? ");
         params.add(type.trim());
     }
     if(decade !=null && !decade.isEmpty()){
        int startYear = Integer.parseInt(decade);
        int endYear = startYear + 9;

        sql.append("AND COIN_YEAR BETWEEN ? AND ? ");
        params.add(startYear);
        params.add(endYear); 
     }
     if("year_desc".equalsIgnoreCase(sort)){
         sql.append(" ORDER BY COIN_YEAR DESC, ID DESC ");
     }else{
         sql.append(" ORDER BY COIN_YEAR ASC, ID ASC ");
     }
     try (java.sql.Connection con = DB.getConnection();
         java.sql.PreparedStatement ps = con.prepareStatement(sql.toString())){
         for(int i=0;i<params.size();i++){
             ps.setObject(i+1, params.get(i));
         }
         try(java.sql.ResultSet rs =ps.executeQuery()){
               List<Coin> list = new java.util.ArrayList<>();
            while (rs.next()) {
                Coin c = new Coin();
                c.setId(rs.getInt("ID"));
                c.setType(rs.getString("TYPE"));
                c.setCountry(rs.getString("COUNTRY"));
                c.setDenomination(rs.getString("DENOMINATION"));
                c.setCurrency(rs.getString("CURRENCY"));
                c.setCoinYear(rs.getInt("COIN_YEAR"));
                c.setNotes(rs.getString("NOTES"));
                c.setImageFront(rs.getString("IMAGE_FRONT"));
                c.setImageBack(rs.getString("IMAGE_BACK"));
                list.add(c);   
         }
        return list; 
     }
     
     
     
     
 }
     
     
    }    
public List<Integer> getDecades() throws Exception {
    String sql =
        "SELECT DISTINCT (COIN_YEAR / 10) * 10 AS DECADE " +
        "FROM COINS " +
        "WHERE COIN_YEAR IS NOT NULL " +
        "ORDER BY DECADE";

    List<Integer> decades = new ArrayList<>();

    try (Connection con = DB.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            decades.add(rs.getInt("DECADE"));
        }
    }
    return decades;
}

    }