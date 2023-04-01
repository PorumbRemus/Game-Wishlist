package com.wishlist.wishlist;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AdminListController extends LoginController implements Initializable,Addable{
    @FXML
    private TextField gameField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField publisherField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField priceField;
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
    public GameList list=new GameList();
    @FXML
    public String prepGame(Game tempGame)
    {

        String listOut=tempGame.name+" \""+tempGame.year+"\" \""+tempGame.creator+"\" \""+tempGame.genre+"\" \""+tempGame.price+" RON\"";
        return listOut;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i=0;i<list.Games.size();i++)
        {
            String listout=prepGame(list.Games.get(i));
            myListView.getItems().add(listout);
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
        if (gameField.getText().isBlank() == false && yearField.getText().isBlank() == false && publisherField.getText().isBlank() == false && genreField.getText().isBlank() == false) {
            Add();
        } else {
            addLabel.setText("Completeaza tot");
        }
    }

    public void Add(){
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String gamename=gameField.getText();
        String year=yearField.getText();
        String publisher=publisherField.getText();
        String genre=genreField.getText();
        String price=priceField.getText();
        String insertFields= "INSERT INTO `games`(`ID`,`gamename`, `year`, `creator`, `genre`,`price`) VALUES ('";
        String insertValues=0+"','"+gamename+"','"+year+"','"+publisher+"','"+genre+"','"+price+"')";
        String insertToRegister=insertFields+insertValues;
        String verifyLogin="SELECT count(1) FROM `games` where gamename = '"+ gamename+"'";
        String listOut=gamename+" \""+year+"\" \""+publisher+"\" \""+genre+"\" \""+price+" RON\"";
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
