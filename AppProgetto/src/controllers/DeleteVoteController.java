package controllers;

import database.DatabaseController;
import database.User;
import database.Voto;
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

public class DeleteVoteController implements Initializable {
    @FXML
    private Button eliminaButton;
    @FXML
    private Label labelStudent;
    @FXML
    private TableView<Voto> tabellaVoti = new TableView<>();

    @FXML
    private TableColumn<Voto, String> voto = new TableColumn<>("Voto");
    private String classeScelta;

    private User user;
    private String studentName;
    private DatabaseController db;

    private Connection cnn;

    public void setConnection() {
        db = new DatabaseController("jdbc:mysql://localhost/appscuola", "root", "");
        cnn = db.DB_Connection();
    }
    public void setData(String nome_studente, User user, String className){
        setStudentName(nome_studente);
        this.user = user;
        setClasseScelta(className);
        labelStudent.setText(nome_studente);
        riempiTabella();
    }

    private void riempiTabella(){
        setConnection();
        System.out.println(getStudentName());
        System.out.println(getClasseScelta());
        if (!tabellaVoti.getColumns().contains(voto)) {
            tabellaVoti.getColumns().addAll(voto);
        }
        String[] student = getStudentName().split(" ");
        String name = student[0];
        String surname = student[1];

        try {
            PreparedStatement stmt1 = cnn.prepareStatement(("SELECT subject FROM insegnanti WHERE id=?"));
            stmt1.setInt(1, user.getId());
            ResultSet rs1 = stmt1.executeQuery();
            String subjectQuery = "";
            while (rs1.next()){
                subjectQuery = rs1.getString("subject");
            }


            PreparedStatement stmt = cnn.prepareStatement(("SELECT DISTINCT vote FROM appscuola.voti JOIN appscuola.account ON voti.student_id = account.id WHERE account.name = ? AND account.surname = ? AND voti.subject = ?"));
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, subjectQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int voto_tabella = rs.getInt("vote");
                Voto data = new Voto(voto_tabella);
                tabellaVoti.getItems().add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminaVoto(ActionEvent event) {
        Voto voto = tabellaVoti.getSelectionModel().getSelectedItem();
        if (voto != null) {
            eliminaVotoSelezionato(voto);
            tabellaVoti.getItems().remove(voto);
        }
    }
    private void eliminaVotoSelezionato(Voto voto) {
        try {
            int student_id = 0;
            String[] student = getStudentName().split(" ");
            String name = student[0];
            String surname = student[1];
            PreparedStatement stmt1 = cnn.prepareStatement("SELECT id FROM account WHERE name=? AND surname=?");
            stmt1.setString(1, name);
            stmt1.setString(2, surname);
            ResultSet rs = stmt1.executeQuery();
            while (rs.next()){
                student_id = rs.getInt("id");
            }

            PreparedStatement stmt = cnn.prepareStatement("DELETE FROM voti WHERE vote=? AND student_id = ?");
            stmt.setInt(1, voto.getVoto());
            stmt.setInt(2, student_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void back(ActionEvent e)throws IOException {
        Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../scenes/Student_list.fxml"));
        AnchorPane root = loader.load();
        StudentListController studentListController = loader.getController();
        studentListController.setConnection();
        stage.setScene(new Scene(root, 1000, 700));
        stage.setTitle("Registro Elettronico | "+ classeScelta +" Visualizza studenti");
        studentListController.displayName(classeScelta, user);
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setClasseScelta(String classeScelta) {
        this.classeScelta = classeScelta;
    }

    public String getClasseScelta() {
        return classeScelta;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        voto.setCellValueFactory(new PropertyValueFactory<>("Voto"));
        eliminaButton.setOnAction(this::eliminaVoto);
    }
}
