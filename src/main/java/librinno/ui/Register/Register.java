package main.java.librinno.ui.Register;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
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
        String type = (isStudent.isSelected())?User.student:(isProfessor.isSelected())?User.professor:(isInstructor.isSelected())?User.instructor:(isTA.isSelected())?User.ta:User.vProfessor;
        if ((user.isEmpty())||pass.isEmpty()||addressText.isEmpty()||phoneNum.isEmpty() || mail.isEmpty()){
            Assist.error();
        }
        else{
            Database db= new Database();
            //TODO Change constructor: add email.
            Database.userCreation(new User(user,phoneNum, addressText, type, pass));

            Assist.closeStage(confirm);
        }
    }

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
