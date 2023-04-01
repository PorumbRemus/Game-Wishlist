package com.wishlist.wishlist;

import java.sql.*;

public class DatabaseConnection {
   public Connection databaseLink;
   public Connection getConnection(){
       String databaseName= "proiect";
       String databaseUser= "root";
       String databasePassword= "remus";
       String url= "jdbc:mysql://localhost:3307/"+databaseName;
       try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink=DriverManager.getConnection(url, databaseUser,databasePassword);
       }catch(Exception e){
           e.printStackTrace();
           e.getCause();
       }
       return databaseLink;
    }

}
