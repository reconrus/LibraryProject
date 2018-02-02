package gui.Login.Register;

import gui.assist.Assist;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Register {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField password_again;

    @FXML
    private JFXTextField mail;

    @FXML
    private JFXToggleButton isStudent;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton confirm;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    @FXML
    void confirm(ActionEvent event) {
        String user= username.getText();
        String pass= password.getText();
        String pass2= password_again.getText();
        String email= mail.getText();
        Boolean isStudent= confirm.isArmed();
        System.out.println(isStudent);
        if ((user.isEmpty())||pass.isEmpty()||pass2.isEmpty()||email.isEmpty()){
            Assist.error();
        }
        else{
            //add him db
            Assist.closeStage(confirm);
        }
    }

}
