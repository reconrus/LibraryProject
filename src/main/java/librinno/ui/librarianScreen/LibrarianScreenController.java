package main.java.librinno.ui.librarianScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;

public class LibrarianScreenController {

    @FXML
    private JFXButton addDoc;

    @FXML
    private JFXButton deleteDoc;

    @FXML
    private JFXButton issue;

    @FXML
    private JFXButton editDoc;

    @FXML
    private JFXTreeTableView<?> tableBook;

    @FXML
    private JFXButton addPatron;

    @FXML
    private JFXButton deletePatron;

    @FXML
    private JFXButton editPatron;

    @FXML
    void addDoc(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../addbook/addbook.fxml"));

    }

    @FXML
    void addPatron(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../Register/register.fxml"));

    }

    @FXML
    void deleteDoc(ActionEvent event) {

    }

    @FXML
    void deletePatron(ActionEvent event) {

    }

    @FXML
    void editDoc(ActionEvent event) {

    }

    @FXML
    void editPatron(ActionEvent event) {

    }

    @FXML
    void issue(ActionEvent event) {

    }

}
