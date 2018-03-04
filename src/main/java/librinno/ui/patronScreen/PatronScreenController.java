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
    private  Text cardNumber;

    @FXML
    private  Text fullName;

    @FXML
    private  Text phone;

    @FXML
    private  Text address;

    @FXML
    private  Text bookCount;

    @FXML
    private  Text type;

    @FXML
    private JFXTreeTableView<?> reserveTable;

    private User user;

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);

        //Show login screen
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));

    }

    public void setUserInfo(User user){
        cardNumber.setText(String.valueOf(user.get_card_number()));
        fullName.setText(user.get_name());
        phone.setText(user.get_number());
        address.setText(user.get_adress());
        type.setText(user.get_type());
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
