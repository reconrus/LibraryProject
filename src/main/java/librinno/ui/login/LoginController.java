package main.java.librinno.ui.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginController {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private CheckBox isALibrarian; //TODO needed to show both screens (librarian and patron) on the presentation

    public LoginController(){
    }

    @FXML
    private void initialize(){

    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException {

        Boolean checkbox = isALibrarian.isSelected();

        closeStage();

        if(checkbox)
            loadLibrarian();
        else loadPatron();
    }

    private void closeStage() {
        ((Stage) login.getScene().getWindow()).close();
    }

    private void loadLibrarian() throws IOException {

            Parent parent = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/librarianScreen/LibrarianScreen.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.show();

    }


    private void loadPatron() throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronScreen.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();

    }
}
