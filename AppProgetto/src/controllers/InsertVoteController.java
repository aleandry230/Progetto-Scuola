package controllers;

import database.DatabaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class InsertVoteController {
    private String displayName;
    private int id;
    @FXML
    private Label classLabel;
    @FXML
    private TextField voteField;

    @FXML
    private TextField subjectField;
    @FXML
    private Button saveButton;
    private DatabaseController db;

    private Connection cnn;

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }

    @FXML
    private void saveData() {
        db.INSERT_VOTO( 12, voteField.getText(), subjectField.getText());
        System.out.println("Dati inseriti nel database.");
        voteField.clear();
        subjectField.clear();

    }

    @FXML
    private void back(ActionEvent e)throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/Home.fxml"));
        AnchorPane root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setConnection();
        stage.setScene(new Scene(root, 1000, 700));
        stage.setTitle("Registro Elettronico | Inserisci Voto");
        homeController.displayNameCallBack(displayName, getId());
    }

    public void displayName(String className,String displayName, int id){
        classLabel.setText(className);
        setDisplayName(displayName);
        setId(id);
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
