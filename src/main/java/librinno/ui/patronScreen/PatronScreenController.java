package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import main.java.librinno.model.Patron;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class PatronScreenController {


    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton copiesButton;

    @FXML
    private static Text cardNumber;

    @FXML
    private static Text fullName;

    @FXML
    private static Text phone;

    @FXML
    private static Text address;

    @FXML
    private static Text bookCount;

    @FXML
    private static Text type;

    @FXML
    private JFXTreeTableView<?> reserveTable;

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);

        //Show login screen
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));

    }

    public static void setUserInfo(Patron patron){
        cardNumber.setText(String.valueOf(patron.get_card_number()));
        fullName.setText(patron.get_name());
        phone.setText(patron.get_number());
        address.setText(patron.get_adress());
        type.setText(patron.get_type());
        //TODO: Количество копий, взятых юзером: bookCount.setText();
    }

    @FXML
    private void showCopies(ActionEvent event)throws IOException{
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));


    }

    @FXML
    private void reserve(ActionEvent event){

    }
}
