package main.java.librinno.ui.login;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import java.io.IOException;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdk.management.resource.internal.inst.DatagramDispatcherRMHooks;
import main.java.librinno.model.Database;
import main.java.librinno.model.Patron;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.patronScreen.PatronScreenController;

import javax.xml.crypto.Data;


public class LoginController {
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXButton login;

    public LoginController() {
    }

    void loadPatron(String id) throws IOException, SQLException {
        if (id.isEmpty()) {
            Assist.error();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../patronScreen/patronScreen.fxml"));
            Parent parent = loader.load();
            PatronScreenController reg = (PatronScreenController) loader.getController();
            Database db = new Database();
            reg.setUserInfo(db.get_information_about_the_user(Integer.parseInt(id)));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
            Assist.closeStage(login);
        }
    }

    @FXML
    private void loginAction(ActionEvent event) throws IOException, SQLException {
        String id = username.getText();
        String pass = password.getText();

        if(!id.isEmpty() && !pass.isEmpty()){
            if(authorization(id, pass))
                Assist.closeStage(login);
        }
        else Assist.authorizationError();
    }

    boolean authorization(String id, String pass) throws SQLException, IOException {

        Database db = new Database();
        String type = db.authorization(Integer.parseInt(id), pass);

        if(type.equals("Librarian")) {
            loadLibrarian();
            return true;
        }
        if(type.equals("Student") || type.equals("Faculty")){
            loadPatron(id);
            return true;
        }

        Assist.authorizationError();
        return false;
    }

    private void loadLibrarian() throws IOException {
        Assist.loadStage(getClass().getResource("../librarianScreen/LibrarianScreen.fxml"));
    }


    /* FOR TESTs */

    @FXML
    private void setLibrarian(){
        username.setText("33");
        password.setText("124");
    }

    @FXML
    private void setFaculty(){
        username.setText("31");
        password.setText("1");
    }

    @FXML
    private void setStudent(){
        username.setText("32");
        password.setText("1");
    }


}
