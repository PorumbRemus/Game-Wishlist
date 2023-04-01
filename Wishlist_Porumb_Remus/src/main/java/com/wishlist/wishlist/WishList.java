package com.wishlist.wishlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WishList implements Selectable{
    int uid;

    public List<Game> Wishlist=new ArrayList<>();
    public WishList(int user_id){
        this.uid=user_id;
        select_query();
    }
    @Override
    public void select_query() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String connectQuery = "Select * from `wishlist` where user_id='"+this.uid+"'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOut = statement.executeQuery(connectQuery);
            while(queryOut.next()) {
                int ID=queryOut.getInt("ID");
                String name = queryOut.getString("Game");
                int year = queryOut.getInt("year");
                String creator = queryOut.getString("creator");
                String genre = queryOut.getString("genre");
                int price = queryOut.getInt("price");
                Game tempGame=new Game(ID,name,year,creator,genre,price);
                addGame(tempGame);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addGame(Game game){

        Wishlist.add(game);}
    public void showList(){
        System.out.println(Wishlist.get(0));
    }
}
