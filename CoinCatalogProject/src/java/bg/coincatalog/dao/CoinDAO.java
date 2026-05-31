        /*
         * To change this license header, choose License Headers in Project Properties.
         * To change this template file, choose Tools | Templates
         * and open the template in the editor.
         */
        package bg.coincatalog.dao;

    
        import bg.coincatalog.db.DB;
        import bg.coincatalog.model.Banknote;
        import bg.coincatalog.model.CatalogItem;
        import bg.coincatalog.model.Coin;
        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.util.ArrayList;
        import java.util.List;


        public class CoinDAO implements CatalogDAO{

        @Override
        public List<CatalogItem> getAll() throws Exception{
            String sql = "SELECT ID, TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, " +
                 "IMAGE_FRONT, IMAGE_BACK " +
                 "FROM COINS ORDER BY ID";

            List<CatalogItem> result = new ArrayList<>();

           try(Connection con = DB.getConnection();
               PreparedStatement ps =con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()){
                   while(rs.next()){
                        result.add(mapRowToItem(rs));
                   }
               }
           return result;
           }
        
         @Override
        public List<CatalogItem> getByCountry(String country) throws Exception{
           String sql = "SELECT ID, TYPE, COUNTRY, DENOMINATION, CURRENCY, COIN_YEAR, NOTES, " +
            "IMAGE_FRONT, IMAGE_BACK " +
            "FROM COINS WHERE COUNTRY = ? ORDER BY ID";

           List<CatalogItem> result = new ArrayList<>();
           try(Connection con = DB.getConnection();
                   PreparedStatement ps = con.prepareStatement(sql)){
                   ps.setString(1, country);

                   try(ResultSet rs = ps.executeQuery()){
                       while(rs.next()){
                        result.add(mapRowToItem(rs));
                       }
                   }
           }
           return result;
        }

        @Override
        public void insert(CatalogItem c) throws Exception{
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

      @Override
      public void deleteById(int id) throws Exception{
          String sql = "DELETE FROM COINS WHERE ID = ?";

          try(Connection con =DB.getConnection();
                  PreparedStatement ps =con.prepareStatement(sql)){
              ps.setInt(1, id);
              ps.executeUpdate();
          }
      }

     @Override
     public int countAll() throws Exception{
         String sql = "SELECT COUNT(*) AS C FROM COINS";
         try(Connection con= DB.getConnection();
              PreparedStatement ps =con.prepareStatement(sql);
               ResultSet rs = ps.executeQuery()){
             rs.next();
             return rs.getInt(1);
         }
     }

     @Override
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

     @Override
     public List<CatalogItem> search(String country, String type,String decade,String sort) throws Exception{
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
                   List<CatalogItem> list = new java.util.ArrayList<>();
                while (rs.next()) {
                    list.add(mapRowToItem(rs));   
             }
            return list; 
         }
     }
        }

     @Override
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
  

private CatalogItem mapRowToItem(ResultSet rs) throws Exception{
String dbType = rs.getString("TYPE");
CatalogItem item;

if("BANKNOTE".equalsIgnoreCase(dbType)){
   item = new Banknote();    
}else{
    item = new Coin();
}
item.setId(rs.getInt("ID"));
item.setType(dbType);
item.setCountry(rs.getString("COUNTRY"));
item.setDenomination(rs.getString("DENOMINATION"));
item.setCurrency(rs.getString("CURRENCY"));
item.setCoinYear(rs.getInt("COIN_YEAR"));
item.setNotes(rs.getString("NOTES"));
item.setImageFront(rs.getString("IMAGE_FRONT"));
item.setImageBack(rs.getString("IMAGE_BACK"));

return item;
}
}