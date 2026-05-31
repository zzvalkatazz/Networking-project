/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.coincatalog.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author vk300
 */
public class DB {
private static final String URL="jdbc:derby://localhost:1527/coincatalog";
private static final String USER = "app";
private static final String PASS = "app";

public static Connection getConnection() throws SQLException{
    Connection c = DriverManager.getConnection(URL, USER, PASS);
    System.out.println("CONNECTED TO: " + c.getMetaData().getURL());
    return c;
}
}
