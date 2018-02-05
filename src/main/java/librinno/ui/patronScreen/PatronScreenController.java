package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 *
 */
public class PatronScreenController {

    @FXML
    private JFXTextField patronID;
    @FXML
    private JFXTextField patronName;
    @FXML
    private JFXTextField patronPhone;
    @FXML
    private JFXTextField patronAddress;



    @FXML
    private void logoutAction(ActionEvent event) throws IOException {

        closeStage();

        //Show login screen
        Parent parent = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();

    }

    private void closeStage() {
        ((Stage) patronID.getScene().getWindow()).close();
    }




}
