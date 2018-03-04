package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.text.Text;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;
/**
 *
 */
public class PatronScreenController {


    @FXML
    private JFXButton logout;

    User user;

    @FXML
    private JFXButton copiesButton;

    @FXML
    public Text cardNumber;

    @FXML
    public  Text fullName;

    @FXML
    private  Text phone;

    @FXML
    private Text address;

    @FXML
    private Text bookCount;

    @FXML
    private Text type;

    @FXML
    private JFXTreeTableView<?> reserveTable;

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
        System.out.println(user.get_name());
    }

    public void setUserInfo(User patron){
        cardNumber.setText(String.valueOf(patron.get_card_number()));
        fullName.setText(patron.get_name());
        phone.setText(patron.get_number());
        address.setText(patron.get_adress());
        type.setText(patron.get_type());
        user=patron;
        //TODO: ?????????? ?????, ?????? ??????: bookCount.setText();
    }

    @FXML
    private void showCopies(ActionEvent event)throws IOException{
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));


    }

    @FXML
    private void reserve(ActionEvent event){

    }
}
