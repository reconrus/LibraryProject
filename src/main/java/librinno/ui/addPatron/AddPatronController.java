package main.java.librinno.ui.addPatron;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

public class AddPatronController {

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXRadioButton isStudent;
    @FXML
    private JFXRadioButton isProfessor;
    @FXML
    private JFXRadioButton isInstructor;
    @FXML
    private JFXRadioButton isTA;
    @FXML
    private JFXRadioButton isVisitingProfessor;

    @FXML
    private JFXButton confirm;
    @FXML
    private JFXButton cancel;

    /**
     * closes window without adding patron to database
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    /**
     * adds patron to database and closes the window
     * @param event
     */
    @FXML
    void confirm(ActionEvent event) {
        String user= name.getText();
        String pass= password.getText();
        String addressText= address.getText();
        String phoneNum= phone.getText();
        String mail = email.getText();
        String type = (isStudent.isSelected())?User.student:(isProfessor.isSelected())?User.professor:(isInstructor.isSelected())?User.instructor:(isTA.isSelected())?User.ta:User.vProfessor;
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty() || mail.isEmpty()){
            Assist.error();
        }
        else{

            if(Assist.isValidEmailAddress(mail)) {
                Database.userCreation(new User(user, phoneNum, addressText, type, pass, mail));
                Assist.closeStage(confirm);
            }
            else Assist.emailError();
        }
    }

    /**
     * makes toogle buttons exclude each other
     */
    @FXML
    void initialize(){
        final ToggleGroup types = new ToggleGroup();

        isStudent.setToggleGroup(types);
        isProfessor.setToggleGroup(types);
        isInstructor.setToggleGroup(types);
        isTA.setToggleGroup(types);
        isVisitingProfessor.setToggleGroup(types);

    }

}
