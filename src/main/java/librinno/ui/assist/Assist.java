package main.java.librinno.ui.assist;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;

public class Assist {
    public static void loadStage(URL fxml) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void loadStageWait(URL fxml) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
    }

    public static void loadScreen(URL fxml, Button butt) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        ((Stage) butt.getScene().getWindow()).setScene(new Scene(parent));

    }
    public static void error(){
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Error");
        error.setContentText("You left empty blanks.");
        error.showAndWait();
    }
    public static void closeStage(Button butt){
        ((Stage) butt.getScene().getWindow()).close();
    }

    public static void authorizationError(){
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Error");
        error.setContentText("Authorization error");
        error.showAndWait();
    }

    public static void emailError(){
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Error");
        error.setContentText("Wrong e-mail");
        error.showAndWait();
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
