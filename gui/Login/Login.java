package gui.Login;

import gui.assist.Assist;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.io.IOException;

public class Login{

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton register;

    @FXML
    private JFXButton login;

    @FXML
    void gotoRegister(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("register/register.fxml"),"Registration",true);
    }

    @FXML
    void tryLogin(ActionEvent event) throws IOException {
        String user= username.getText();
        String pass= password.getText();
        if (user.isEmpty()||pass.isEmpty()){
            Assist.error();
        }
        else {
            //check if he is in db; also return true - if he is Patron, and false- if he is librarian
            Assist.closeStage(login);
            if(user.equals("patron")){ //later will change for normal checking
                Assist.loadStage(getClass().getResource("../menu/menuforpatron.fxml"), "Menu",true);
            }
            else {
                Assist.loadStage(getClass().getResource("../menu/menu.fxml"), "Menu", true);
            }
        }
    }

}
