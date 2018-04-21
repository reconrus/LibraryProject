package main.java.librinno.ui.assist;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;

public class Assist {
    /**
     * loads stage from given URL
     * @param fxml - URL of the stage you want to load
     * @throws IOException
     */
    public static void loadStage(URL fxml) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * loads stage from given URL and permits code(following lines in method-caller) from executing until stage is closed
     * @param fxml - URL of the stage you want to load
     * @throws IOException
     */
    public static void loadStageWait(URL fxml) throws IOException {
        Parent parent= FXMLLoader.load(fxml);
        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.showAndWait();
    }

    /**
     * shows error screen to user with given parameters
     * @param header - header of the error
     * @param body - message you want to show
     */
    public static void error(String header, String body){
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText(header);
        error.setContentText(body);
        error.showAndWait();
    }

    /**
     * shows error screen to user
     */
    public static void error(){
        error("Error", "You left empty fields");
    }

    /**
     * closes stage where this button exists
     * @param butt - button from the screen you want to close
     */
    public static void closeStage(Button butt){
        ((Stage) butt.getScene().getWindow()).close();
    }

    /**
     * shows user error if he made an error while logging in
     */
    public static void authorizationError(){
        error("Error","Authorization error");
    }

    /**
     * shows an error to user if in ID field of login screen he wrote any symbols aside from integers
     */
    public static void wrongIDError(){
        error("Error", "ID should contain only numerical symbols");
    }

    /**
     * shows an error to a user if he wrote wrong type of e-mail
     */
    public static void emailError(){
        error("Error","Wrong e-mail");
    }

    /**
     * show error if user wrote false information about database
     */
    public static void dbCreationError(){
        error("Database Creation Error","Wrong Database Information");
    }

    /**
     * verifies email address passed to it and shows an error if it's wrong
     * @param email
     * @return
     */
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

}
