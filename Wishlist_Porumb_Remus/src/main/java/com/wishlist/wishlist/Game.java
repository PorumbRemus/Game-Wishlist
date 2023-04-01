package com.wishlist.wishlist;

import javax.security.auth.login.CredentialException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Game {
    public int ID;
    public String name;
    public int year;
    public String creator;
    public String genre;
    public int price;

    public Game(int ID, String name, int year, String creator, String genre, int price) {
        this.ID = ID;
        this.name = name;
        this.year = year;
        this.creator = creator;
        this.genre = genre;
        this.price = price;

    }

    public void Sale(int salePercentage)
    {

    }
}
