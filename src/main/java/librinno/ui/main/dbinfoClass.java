package main.java.librinno.ui.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
        FileWriter wr = new FileWriter("dbinf.txt");
        wr.write(url.getText());
        wr.write("\n");
        wr.write(user.getText());
        wr.write("\n");
        wr.write(pass.getText());
        wr.close();
        Assist.closeStage(confirm);
    }

}
