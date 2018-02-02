package gui.assist;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class Assist {
    public static void loadStage(URL fxml, String title,Boolean wait) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        if (wait){
            stage.showAndWait();
        }
        else {
            stage.show();
        }
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
}
