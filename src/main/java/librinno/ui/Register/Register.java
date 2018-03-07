package main.java.librinno.ui.Register;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Database;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

public class Register {

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXToggleButton isStudent;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXButton cancel;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    @FXML
    void confirm(ActionEvent event) {
        String user= name.getText();
        String pass= password.getText();
        String addressText= address.getText();
        String phoneNum= phone.getText();
        Boolean isStudent= confirm.isArmed();
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty()){
            Assist.error();
        }
        else{
            Database db= new Database();
            if (isStudent){
                Database.userCreation(new User(user,phoneNum, addressText, "Student", pass));
            }
            else{
                Database.userCreation(new User(user,phoneNum, addressText, "Faculty", pass));
            }
            Assist.closeStage(confirm);
        }
    }

}
