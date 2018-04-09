package main.java.librinno.ui.adminScreen;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;
import java.util.LinkedList;

public class adminScreenController {

    @FXML
    private JFXButton addPatron;

    @FXML
    private JFXButton editPatron;

    @FXML
    private JFXButton deletePatron;

    @FXML
    private TableView<Librarian> tableUser;

    @FXML
    private TableColumn<Librarian, Integer> userID;

    @FXML
    private TableColumn<Librarian, String> userName;

    @FXML
    private TableColumn<Librarian, String> userPhone;

    @FXML
    private TableColumn<Librarian, String> userAddress;

    @FXML
    private TableColumn<Librarian, String> userType;

    @FXML
    private TableColumn<Librarian, String> privilege;

    @FXML
    private JFXButton logout;


    @FXML
    void initialize(){
        showTableUser();
    }
    @FXML
    void showTableUser(){
        userID.setCellValueFactory(new PropertyValueFactory("card_number"));
        userName.setCellValueFactory(new PropertyValueFactory("name"));
        userAddress.setCellValueFactory(new PropertyValueFactory("adress"));
        userPhone.setCellValueFactory(new PropertyValueFactory("phoneNumber"));
        userType.setCellValueFactory(new PropertyValueFactory("type"));
        privilege.setCellValueFactory(new PropertyValueFactory("privileges"));
        LinkedList<Librarian> users= Librarian.getAllLibrarians();

        tableUser.getItems().setAll(users);
    }


    @FXML
    void addPatron(ActionEvent event) throws IOException {
        Assist.loadStageWait(getClass().getResource("/main/java/librinno/ui/Register/register.fxml"));
        showTableUser();
    }

    @FXML
    void deletePatron(ActionEvent event) {

    }

    @FXML
    void editPatron(ActionEvent event) {

    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
    }

    @FXML
    void showTableUser(ActionEvent event) {
        showTableUser();
    }

}
