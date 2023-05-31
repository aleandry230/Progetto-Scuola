package controllers;
import database.DatabaseController;
import database.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;

public class LoginController{
    @FXML
    private TextField emailField;


    @FXML
    private PasswordField passwordField;

    private boolean isLogged;

    private DatabaseController db;
    private Connection cnn;

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }

    @FXML
    private void login(ActionEvent e) throws IOException{
        String username = emailField.getText();
        String password = passwordField.getText();
        System.out.println(db.isLogin(username, password));
        setLogged(db.isLogin(username, password));

        if(isLogged()) {
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/Home.fxml"));
            AnchorPane root = loader.load();
            HomeController homeController = loader.getController();
            homeController.setConnection();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro Elettronico | Home");
            User user = db.getUserInfo(username, password);
            homeController.displayName(user.getName(), user.getSurname());
        }
    }

}
