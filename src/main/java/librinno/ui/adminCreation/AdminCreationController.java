package main.java.librinno.ui.adminCreation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.librinno.model.Admin;
import main.java.librinno.model.Database;
import main.java.librinno.ui.assist.Assist;

import javax.xml.crypto.Data;

public class AdminCreationController {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton confirm;

    /**
     * closes window without adding admin to database
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    /**
     * reads information from fields, adds admin to database, closes window
     * @param event
     */
    @FXML
    void confirm(ActionEvent event) {
        String user= name.getText();
        String pass= password.getText();
        String addressText= address.getText();
        String phoneNum= phone.getText();
        String mail = email.getText();
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty() || mail.isEmpty()){
            Assist.error();
        }
        else{
            if(Assist.isValidEmailAddress(mail)) {
                Database.admin_creation(new Admin(user, phoneNum, addressText,1,"Admin",  pass, mail));
                int id = Database.getAdminID();
                Alert idAlert = new Alert(Alert.AlertType.CONFIRMATION);
                idAlert.setHeaderText("Admin created");
                idAlert.setContentText("Admin's ID: " + String.valueOf(id));
                idAlert.showAndWait();
                Assist.closeStage(confirm);
            }
            else Assist.emailError();
        }

    }

}
