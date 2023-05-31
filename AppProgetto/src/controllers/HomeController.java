package controllers;
import database.DatabaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    private String displayName;
    private int id;
    @FXML
    private ChoiceBox<String> listaClassi;

    @FXML
    private Label nameLabel;


    private DatabaseController db;

    private Connection cnn;

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }

    private void loadClasses() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();

        // Esegui la query per ottenere i dati dal database
        try {
            PreparedStatement stmt = cnn.prepareStatement(("SELECT * FROM classe INNER JOIN classi_insegnanti WHERE teacher_id=?"));
            stmt.setInt(1, getId());
            ResultSet rs = stmt.executeQuery();

            // Pulisci il ChoiceBox
            listaClassi.getItems().clear();

            // Aggiungi i dati ottenuti dal database al ChoiceBox
            while (rs.next()) {

                String className = rs.getString("class_name");
                listaClassi.getItems().add(new String(className));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertVote(ActionEvent e)throws IOException {
        String classeScelta = listaClassi.getSelectionModel().getSelectedItem();
        if(classeScelta!=null){
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/InsertVote.fxml"));
            AnchorPane root = loader.load();
            InsertVoteController insertVoteController = loader.getController();
            insertVoteController.setConnection();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro Elettronico | Inserisci Voto");
            insertVoteController.displayName(classeScelta, displayName,getId());
        }
    }



    public void displayName(String displayName,int id){
        setDisplayName(displayName);
        setId(id);
        nameLabel.setText(displayName);

    }
    public void displayNameCallBack(String displayName, int id){
        setDisplayName(displayName);
        setId(id);
        nameLabel.setText(displayName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadClasses();
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
