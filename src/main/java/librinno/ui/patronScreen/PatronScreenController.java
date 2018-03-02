package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;

/**
 *
 */
public class PatronScreenController {


    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton copiesButton;

    @FXML
    private Text cardNumber;

    @FXML
    private Text fullName;

    @FXML
    private Text phone;

    @FXML
    private Text address;

    @FXML
    private Text bookCount;

    @FXML
    private JFXTreeTableView<?> reserveTable;

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);

        //Show login screen
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));

    }

    @FXML
    private void showCopies(ActionEvent event)throws IOException{

        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));

    }





}
