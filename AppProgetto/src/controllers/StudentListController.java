package controllers;

import database.DatabaseController;
import database.Dati;
import database.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentListController implements Initializable {
    private User user;
    @FXML
    private TableView<Dati> tabellaStudenti = new TableView<>();

    @FXML
    private TableColumn<Dati, String> studente = new TableColumn<>("Studente");
    @FXML
    private Label classLabel;
    private String className;
    private String nomeStudente;
    private DatabaseController db;

    private Connection cnn;

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }

    private void riempiTabella(){
        setConnection();
        if (!tabellaStudenti.getColumns().contains(studente)) {
            tabellaStudenti.getColumns().addAll(studente);
        }
        try {
            PreparedStatement stmt = cnn.prepareStatement(("SELECT DISTINCT a.name, a.surname FROM appscuola.account a JOIN appscuola.classe c WHERE class_id IN (SELECT id from appscuola.classe WHERE class_name=?)"));
            stmt.setString(1, getClassName());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String nome_studente = name + " " + surname;
                setNomeStudente(nome_studente);
                Dati data = new Dati(nome_studente);
                tabellaStudenti.getItems().add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void inserisciVoto(ActionEvent e)throws IOException {
        if(nomeStudente!=null){
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/InsertVote.fxml"));
            AnchorPane root = loader.load();
            InsertVoteController insertVoteController = loader.getController();
            insertVoteController.setConnection();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro Elettronico | "+ nomeStudente +" Inserisci voto");
            insertVoteController.setData(nomeStudente, user, className);
        }
    }

    @FXML
    private void rimuoviVoto(ActionEvent e)throws IOException {
        if(nomeStudente!=null){
            Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/DeleteVote.fxml"));
            AnchorPane root = loader.load();
            DeleteVoteController deleteVoteController = loader.getController();
            deleteVoteController.setConnection();
            stage.setScene(new Scene(root, 1000, 700));
            stage.setTitle("Registro Elettronico | "+ nomeStudente +" Rimuovi voto");
            deleteVoteController.setData(nomeStudente, user, className);
        }
    }

    @FXML
    private void back(ActionEvent e)throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/Home.fxml"));
        AnchorPane root = loader.load();
        HomeController homeController = loader.getController();
        homeController.setConnection();
        stage.setScene(new Scene(root, 1000, 700));
        stage.setTitle("Registro Elettronico | Home");
        homeController.displayNameCallBack(user);
    }


    public void displayName(String class_name, User user) {
        classLabel.setText(class_name);
        this.user = user;
        setClassName(class_name);
        riempiTabella();
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setNomeStudente(String nomeStudente) {
        this.nomeStudente = nomeStudente;
    }

    public String getNomeStudente() {
        return nomeStudente;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studente.setCellValueFactory(new PropertyValueFactory<>("Studente"));
        tabellaStudenti.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // Assicurati che il click sia singolo
                Dati selectedData = tabellaStudenti.getSelectionModel().getSelectedItem();
                if (selectedData != null) {
                    nomeStudente = selectedData.getStudente();
                }
            }
        });
    }
}
