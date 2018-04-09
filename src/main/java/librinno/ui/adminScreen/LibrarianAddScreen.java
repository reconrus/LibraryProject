package main.java.librinno.ui.adminScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

public class LibrarianAddScreen {

    private Librarian librarianEd;

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
    private JFXRadioButton lev1;

    @FXML
    private JFXRadioButton lev2;

    @FXML
    private JFXRadioButton lev3;

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
        String mail = email.getText();
        String privilege = (lev1.isSelected())? Librarian.lev1:(lev2.isSelected())?Librarian.lev2:Librarian.lev3;
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty() || mail.isEmpty()){
            Assist.error();
        }
        else {
            if(Assist.isValidEmailAddress(mail)) {
                Database db = new Database();
                Database.userCreation(new Librarian(user, phoneNum, addressText, 10000,"Librarian "+privilege, pass, mail));
                Assist.closeStage(confirm);
            }
            else Assist.emailError();
        }
    }

    @FXML
    void initialize(){

        final ToggleGroup types = new ToggleGroup();

        lev1.setToggleGroup(types);
        lev2.setToggleGroup(types);
        lev3.setToggleGroup(types);

    }
}
