package main.java.librinno.ui.editPatron;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
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
        else {
            userEd.setAdress(addressText);
            userEd.setName(user);
            userEd.setPassword(pass);
            userEd.setPhoneNumber(phoneNum);
            userEd.setType(type);
            //TODO userEd.setEmail(mail);
            Librarian.modifyUser(userEd);
            Assist.closeStage(confirm);
        }
    }


    public void passGUI(User user){
        id.setText(user.getCardNumberAsString());
        id.setEditable(false);
        name.setText(user.getName());
        phone.setText(user.getPhoneNumber());
        password.setText(user.getPassword());
        address.setText(user.getAdress());
        //TODO email.setText(user.getEmail());
        String type = user.getType();
        if(type.equals(User.student)) isStudent.setSelected(true);
        if(type.equals(User.professor)) isProfessor.setSelected(true);
        if(type.equals(User.instructor)) isInstructor.setSelected(true);
        if(type.equals(User.ta)) isTA.setSelected(true);
        if(type.equals(User.vProfessor)) isVisitingProfessor.setSelected(true);

        userEd=user;
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
