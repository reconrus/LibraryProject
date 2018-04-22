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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.Database;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.librarianScreen.LibrarianScreenController;
import main.java.librinno.ui.patronScreen.PatronScreenController;


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronScreen.fxml"));
            Parent parent = loader.load();
            PatronScreenController reg = (PatronScreenController) loader.getController();
            Database db = new Database();
            reg.setUserInfo(db.getInformationAboutTheUser(Integer.parseInt(id)));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
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

        //If id contains non-numerical symbols, catches exception
        try{
            String type= db.authorization(Integer.parseInt(id), pass);

            if(type.equals("Admin")) {
                loadAdmin();
                return true;
            }

            if((type.equals("Librarian Priv1"))||(type.equals("Librarian Priv2"))||(type.equals("Librarian Priv3"))) {
                loadLibrarian(id);
                return true;
            }
            if(type.equals(User.student) || type.equals(User.professor) || type.equals(User.instructor) || type.equals(User.ta) || type.equals(User.vProfessor)){
                loadPatron(id);
                return true;
            }

            Assist.authorizationError();

        }
        catch (NumberFormatException e){
            Assist.wrongIDError();
        }

        return false;
    }

    private void loadAdmin() throws IOException {
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/adminScreen/AdminScreen.fxml"));
    }

    private void loadLibrarian(String id) throws IOException, SQLException {
        if (id.isEmpty()) {
            Assist.error();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/librarianScreen/LibrarianScreen.fxml"));
            Parent parent = loader.load();
            LibrarianScreenController reg = loader.getController();
            Database db = new Database();
            reg.setLibrarianInfo(db.getInformationAboutTheLibrarian(Integer.parseInt(id)));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.show();
            Assist.closeStage(login);
        }
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
