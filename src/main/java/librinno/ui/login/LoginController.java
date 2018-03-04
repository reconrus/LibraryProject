package main.java.librinno.ui.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.Database;
import main.java.librinno.model.Patron;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.patronScreen.PatronScreenController;

import javax.xml.crypto.Data;


public class LoginController {
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;
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
    private void loginAction(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {
        Boolean checkbox = isALibrarian.isSelected();

        String id=username.getText();
        String pass= password.getText();

        Assist.closeStage(login);

        if(checkbox)
            loadLibrarian();
        else loadPatron();


        //TODO make an authorization. Different windows for different users


    }

    private void loadLibrarian() throws IOException {
            Assist.loadStage(getClass().getResource("../librarianScreen/LibrarianScreen.fxml"));
    }


    private void loadPatron() throws IOException, SQLException, ClassNotFoundException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../patronScreen/PatronScreen.fxml"));
        Parent parent= loader.load();

        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
