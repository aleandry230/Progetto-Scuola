import controllers.HomeController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

public class Main extends Application {

    private Stage primaryStage;
    private Scene scene1;
    private Scene scene2;

    public static void main(String[] args) {
        launch(args);
    }

   public void start(Stage primaryStage) throws Exception{

       this.primaryStage = primaryStage;
       FXMLLoader scene1Loader = new FXMLLoader(getClass().getResource("scenes/Login.fxml"));
       AnchorPane scene1Root = scene1Loader.load();
       LoginController loginController = scene1Loader.getController();
       loginController.setConnection();
       scene1 = new Scene(scene1Root, 600, 400);

       primaryStage.setTitle("Registro Elettronico | Login");
       primaryStage.getIcons().add(new Image(("./assets/logo.png")));
       primaryStage.setScene(scene1);
       primaryStage.setResizable(false);
       primaryStage.show();

    }








}