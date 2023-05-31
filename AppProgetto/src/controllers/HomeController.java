package controllers;
import database.DatabaseController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.sql.*;

public class HomeController{
    @FXML
    private ChoiceBox<String> listaClassi;
    @FXML
    private Label nameLabel;
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

    private void loadClasses() {
        // Esegui la query per ottenere i dati dal database
        String query = "SELECT class_name FROM classe";
        try {
            Statement stmt = cnn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Pulisci il ChoiceBox
            listaClassi.getItems().clear();

            // Aggiungi i dati ottenuti dal database al ChoiceBox
            while (rs.next()) {
                String className = rs.getString("class_name");
                listaClassi.getItems().add(className);
            }

            // Chiudi il ResultSet e lo Statement
            rs.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayName(String name, String surname){
        nameLabel.setText("Ciao, " + name + " " + surname);
    }
    @FXML
    private void saveData() {
        db.INSERT_VOTO( 12, voteField.getText(), subjectField.getText());
        System.out.println("Dati inseriti nel database.");
        voteField.clear();
        subjectField.clear();

    }
}
