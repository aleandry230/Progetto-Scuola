package controllers;
import database.DatabaseController;
import database.User;
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
    @FXML
    private ComboBox<String> listaClassi;

    @FXML
    private Label nameLabel;


    private static int ID;
    private User user;
    private DatabaseController db;

    private Connection cnn;

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }

    private void loadClasses(int id) {
        setConnection();
        // Esegui la query per ottenere i dati dal database
        try {
            PreparedStatement stmt = cnn.prepareStatement(("SELECT * FROM classi_insegnanti INNER JOIN classe WHERE teacher_id=?"));
            stmt.setInt(1, id);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/Student_list.fxml"));
            AnchorPane root = loader.load();
            StudentListController studentListController = loader.getController();
            studentListController.setConnection();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro Elettronico | "+ classeScelta +" Visualizza studenti");
            studentListController.displayName(classeScelta, user);
        }
    }



    public void displayName(User user){
        this.user = user;
        setID(user.getId());
        nameLabel.setText("Ciao, "+ user.getName());
    }
    public void displayNameCallBack(User user){
        this.user = user;
        nameLabel.setText("Ciao, "+ user.getName());
    }

    public void setID(int ID) {
        this.ID = ID;
        loadClasses(ID);
    }

    public int getID() {
        return ID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       loadClasses(ID);
    }


}
