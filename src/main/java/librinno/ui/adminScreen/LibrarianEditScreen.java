package main.java.librinno.ui.adminScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

public class LibrarianEditScreen {

    //current librarian we want to modify
    private Librarian librarianEd;

    @FXML
    private JFXTextField id;

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

    /**
     * closes window without any modification
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    /**
     * modifies information about user in database and closes window
     * @param event
     */
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
                librarianEd.setAdress(addressText);
                librarianEd.setName(user);
                librarianEd.setPassword(pass);
                librarianEd.setPhoneNumber(phoneNum);
                librarianEd.setType("Librarian "+privilege);
                librarianEd.setPrivileges(privilege);
                librarianEd.setEmail(mail);
                Librarian.modifyUser(librarianEd);
                librarianEd.setType("Librarian");
                Assist.closeStage(confirm);
            }
            else Assist.emailError();
        }
    }

    /**
     * passes information about the librarian to LibrarianEdit screen so that fields were containing current information
     * @param librarian
     */
    public void passGUI(Librarian librarian){
        id.setText(librarian.getCardNumberAsString());
        id.setEditable(false);
        name.setText(librarian.getName());
        phone.setText(librarian.getPhoneNumber());
        password.setText(librarian.getPassword());
        address.setText(librarian.getAdress());
        email.setText(librarian.getEmail());
        String privilege = librarian.getPrivileges();
        if(privilege.equals(Librarian.lev1)) lev1.setSelected(true);
        if(privilege.equals(Librarian.lev2)) lev2.setSelected(true);
        if(privilege.equals(Librarian.lev3)) lev3.setSelected(true);

        librarianEd=librarian;
    }

    /**
     * makes toogle buttons exclude each other
     */
    @FXML
    void initialize(){

        final ToggleGroup types = new ToggleGroup();

        lev1.setToggleGroup(types);
        lev2.setToggleGroup(types);
        lev3.setToggleGroup(types);

    }


}
