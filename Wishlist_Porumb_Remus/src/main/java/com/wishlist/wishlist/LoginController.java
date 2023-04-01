package com.wishlist.wishlist;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import static com.wishlist.wishlist.Config.*;
public class LoginController implements Initializable{
    public static int current_ID;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button LoginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField firstnameField;
    @FXML
    private TextField lastnameField;
    @FXML
    private TextField userSignUp;
    @FXML
    private PasswordField passwordSignUp;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private Button signupButton;
    @FXML
    private Label signupLabel;
    @FXML
    private Label confirmLabel;
    @FXML
    private Label takenUser;
    @FXML
    private AnchorPane loginPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        loginPane.setBackground(new Background(new BackgroundFill(BACKGROUNDCOLOR,null,null)));
    }
    public String getRole(String username){
        String role="";
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String verifyLogin="SELECT role FROM `user_account` where username = '"+ usernameTextField.getText()+"'";
        try
        {
            Statement statement= connectDb.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);
            while (queryResult.next())
            {
                role=queryResult.getString(1);
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }
        return role;
    }
    @FXML
    public void loginButtonAction(ActionEvent event){
        if(usernameTextField.getText().isBlank()==false && enterPasswordField.getText().isBlank()==false){
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password");
        }

    }
    public void cancelButtonAction(ActionEvent event){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
    public void validateLogin(){
        DatabaseConnection connectNow= new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String verifyLogin="SELECT count(1) FROM `user_account` where username = '"+ usernameTextField.getText()+ "' AND password ='"+enterPasswordField.getText()+"'";
        try{
           // Statement id_verif=conncetDb.createStatement();
            Statement statement= connectDb.createStatement();
            ResultSet queryResult=statement.executeQuery(verifyLogin);
            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessageLabel.setText("Congrats!");
                    setCurrent_ID(usernameTextField.getText());
                    String role=getRole(usernameTextField.getText());
                    if(role.equals("admin"))
                    {
                        HelloApplication.changeScene("adminlist.fxml");
                    }
                    if(role.equals("user"))
                    //ResultSet id_query=statement.executeQuery();
                        HelloApplication.changeScene("test.fxml");
                }else{
                    loginMessageLabel.setText("Invalid Login, please try again");
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    public void signupButtonAction(ActionEvent event)
    {
        if(passwordSignUp.getText().equals(repeatPassword.getText())){
            registerUser();
            confirmLabel.setText("");

        }else{
            confirmLabel.setText("Does not match");
        }

    }
    public void registerUser() {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String firstname=firstnameField.getText();
        String lastname=lastnameField.getText();
        String username=userSignUp.getText();
        String password=passwordSignUp.getText();
        String insertFields= "INSERT INTO `user_account`(`firstname`, `lastname`, `username`, `password`,`role`) VALUES ('";
        String insertValues=firstname+"','"+lastname+"','"+username+"','"+password+"','user'"+")";
        String insertToRegister=insertFields+insertValues;
        String verifyLogin="SELECT count(1) FROM `user_account` where username = '"+ username+"'";
        try{
            Statement statement= connectDB.createStatement();
            Statement statement1= connectDB.createStatement();

            ResultSet queryResult=statement1.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    takenUser.setText("User already exists");
                } else {
                    takenUser.setText("");
                    statement.executeUpdate(insertToRegister);
                    signupLabel.setText("User has been registered successfully");
                }
            }


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }
    public void setCurrent_ID(String username)
    {
        DatabaseConnection connectNow=new DatabaseConnection();
        Connection connectDB= connectNow.getConnection();
        String getID="SELECT ID FROM `user_account` where username = '"+ username+"'";
        try{
            Statement statement= connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(getID);
            while (queryResult.next())
            {
                current_ID=queryResult.getInt(1);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }

    }
}