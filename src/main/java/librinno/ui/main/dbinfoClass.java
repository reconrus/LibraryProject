package main.java.librinno.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Database;
import main.java.librinno.model.Main;
import main.java.librinno.ui.assist.Assist;

import java.io.FileWriter;
import java.io.IOException;

public class dbinfoClass {

    @FXML
    private JFXTextField url;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXButton confirm;

    @FXML
    void confirm(ActionEvent event) throws IOException {

        String urlText = url.getText();
        String userText = user.getText();
        String passText = pass.getText();
        Main.setDbUrl(urlText);

        if(Database.creationLocalDB(userText, passText)){
            FileWriter wr = new FileWriter("dbinf.txt");
            wr.write(urlText);
            wr.write("\n");
            wr.write(userText);
            wr.write("\n");
            wr.write(passText);
            wr.close();
            Assist.closeStage(confirm);
        }

        else{
            user.setText("");
            pass.setText("");
            Assist.dbCreationError();
        }


    }

}
