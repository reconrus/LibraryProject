package main.java.librinno.ui.editPatron;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

public class EditPatron {

    private User userEd;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField type;

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
        String typeUs= type.getText();
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty()){
            Assist.error();
        }
        else {
            userEd.setAdress(addressText);
            userEd.setName(user);
            userEd.setPassword(pass);
            userEd.setPhoneNumber(phoneNum);
            userEd.setType(typeUs);
            Librarian.modifyUser(userEd);
            Assist.closeStage(confirm);
        }
    }


    public void passGUI(User user){
        name.setText(user.getName());
        phone.setText(user.getPhoneNumber());
        password.setText(user.getPassword());
        address.setText(user.getAdress());
        type.setText(user.getType());
        id.setText(user.getCardNumberAsString());
        id.setEditable(false);
        userEd=user;
    }


}
