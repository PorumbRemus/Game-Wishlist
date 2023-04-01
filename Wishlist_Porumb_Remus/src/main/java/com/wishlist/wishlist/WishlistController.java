package com.wishlist.wishlist;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class WishlistController extends LoginController implements Initializable,Addable{
    @FXML
    private TextField gameField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField genreField;
    @FXML
    private Button addButton;
    @FXML
    private Label addLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button logoutButton;
    @FXML
    private ListView<String> myListView;
    @FXML
    private ListView<String> wishlist;
    public GameList list=new GameList();
    public WishList wlist=new WishList(current_ID);
    /*public void get_fields(String gamename) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        String verifyLogin = "SELECT * FROM `games` where gamename = '" + gamename + "'";
        Game tempGame = null;
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOut = statement.executeQuery(verifyLogin);
            while (queryOut.next()) {
                int ID = queryOut.getInt("ID");
                String name = queryOut.getString("gamename");
                int year = queryOut.getInt("year");
                String creator = queryOut.getString("creator");
                String genre = queryOut.getString("genre");
                int price = queryOut.getInt("price");
                tempGame = new Game(ID, name, year, creator, genre, price);
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        System.out.println(tempGame.creator);
    }*/
    public void Add()
    {
        AtomicInteger ok= new AtomicInteger();
        AtomicReference<String> substring_name = new AtomicReference<>();
        myListView.setOnMouseClicked(mouseEvent -> {
            addButton.setVisible(true);
        });
        addButton.setOnAction(MouseEvent -> {
            String selectedItem=myListView.getSelectionModel().getSelectedItem();
            if(!wishlist.getItems().contains(selectedItem)) {
                wishlist.getItems().add(selectedItem);
                substring_name.set(selectedItem.substring(0, selectedItem.indexOf("\"")));
                System.out.print(substring_name);
            }
            else
                addLabel.setText("Game already exists");
            DatabaseConnection connectNow=new DatabaseConnection();
            Connection connectDB= connectNow.getConnection();
            String verifyLogin="SELECT * FROM `games` where gamename = '"+ substring_name +"'";
            //get_fields(substring_name.toString());
            //String listOut=gamename+" \""+year+"\" \""+publisher+"\" \""+genre+" \"";
            try{
                Statement statement = connectDB.createStatement();
                ResultSet queryOut = statement.executeQuery(verifyLogin);
                while (queryOut.next()) {
                    String name = queryOut.getString("gamename");
                    int year = queryOut.getInt("year");
                    String creator = queryOut.getString("creator");
                    String genre = queryOut.getString("genre");
                    int price = queryOut.getInt("price");
                    if (queryOut.getString("gamename") == name) {
                        addLabel.setText("Game already exists");
                    } else {
                        addLabel.setText("Game added to list");
                        //myListView.getItems().add(listOut);
                        String insertFields= "INSERT INTO `wishlist`(`user_id`,`Game`, `year`, `creator`, `genre`,`price`) VALUES ('";
                        String insertValues=current_ID+"','"+name+"','"+year+"','"+creator+"','"+genre+"','"+price+"')";
                        String insertToRegister=insertFields+insertValues;
                        statement.executeUpdate(insertToRegister);
                        //initialize();
                    }
                    ok.set(0);
                }


            }catch (Exception e){
                e.printStackTrace();
                e.getCause();
            }

        });


    }
    public String prepGame(Game tempGame)
    {
        String listOut=tempGame.name+" \""+tempGame.year+"\" \""+tempGame.creator+"\" \""+tempGame.genre+"\" \""+tempGame.price+" RON\"";
        return listOut;
    }
    public String prepGameWishlist(Game tempGame)
    {
        String listOut=tempGame.name+" \""+tempGame.price+" RON\"";
        return listOut;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButton.setVisible(false);
        for(int i=0;i<list.Games.size();i++)
        {
            String listout=prepGame(list.Games.get(i));
            myListView.getItems().add(listout);
        }
        Add();
        for(int i=0;i<wlist.Wishlist.size();i++)
        {
            //System.out.println(wlist.Wishlist.get(i).name);
            String listout=prepGameWishlist(wlist.Wishlist.get(i));
            wishlist.getItems().add(listout);
        }

    }

    @FXML
    public void cancelButtonAction(ActionEvent event)
    {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        //cancelButton.setText(Integer.toString(super.current_ID));
        stage.close();
        Platform.exit();
    }
    public void logoutButtonAction(ActionEvent event)
    {
        try {
            HelloApplication.changeScene("hello-view.fxml");
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void AddGameAction(ActionEvent event) {
        Add();
    }

    public void validGame(){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String gamename=gameField.getText();
        String year=yearField.getText();
        String publisher=publisherField.getText();
        String genre=genreField.getText();
        String insertFields= "INSERT INTO `games`(`ID`,`gamename`, `year`, `creator`, `genre`,`price`) VALUES ('";
        String insertValues=0+"','"+gamename+"','"+year+"','"+publisher+"','"+genre+"','"+0+"')";
        String insertToRegister=insertFields+insertValues;
        String verifyLogin="SELECT count(1) FROM `games` where gamename = '"+ gamename+"'";
        String listOut=gamename+" \""+year+"\" \""+publisher+"\" \""+genre+" \"";
        try{
            Statement statement= connectDB.createStatement();
            Statement statement1= connectDB.createStatement();

            ResultSet queryResult=statement1.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    addLabel.setText("Game already exists");
                } else {
                    addLabel.setText("Game added to list");
                    statement.executeUpdate(insertToRegister);
                    myListView.getItems().add(listOut);
                    //initialize();
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
