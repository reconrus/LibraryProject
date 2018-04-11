package main.java.librinno.ui.adminScreen;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.editPatron.EditPatron;
import main.java.librinno.ui.librarianScreen.LibrarianScreenController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class AdminScreenController {

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
    private TableView<LogsForTable> logs;

    @FXML
    private TableColumn<LogsForTable, String> events;

    @FXML
    private TableColumn<LogsForTable, String> date;

    @FXML
    private JFXButton logout;


    @FXML
    void initialize() throws FileNotFoundException {
        showTableUser();
        showLogs();
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
        Assist.loadStageWait(getClass().getResource("/main/java/librinno/ui/adminScreen/AddLibrarian.fxml"));
        showTableUser();
    }

    @FXML
    void deletePatron(ActionEvent event) {
        User user= tableUser.getSelectionModel().getSelectedItem();
        if(user!=null) {
            Librarian.deleteUserById(user.card_number);
            showTableUser();
        }
    }

    @FXML
    void editPatron(ActionEvent event) throws IOException {
        Librarian user= tableUser.getSelectionModel().getSelectedItem();
        if (user==null){
            Assist.error();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/adminScreen/EditLibrarian.fxml"));
            Parent parent = loader.load();
            LibrarianEditScreen reg = loader.getController();
            reg.passGUI(user);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            showTableUser();
        }

    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
    }

    @FXML
    void showLogs() throws FileNotFoundException {
        Scanner sc= new Scanner(new File("myproject.log"));
        ArrayList<LogsForTable> arr= new ArrayList<>();
        while (sc.hasNext()) arr.add(new LogsForTable(sc.nextLine()));
        date.setCellValueFactory(new PropertyValueFactory("date"));
        events.setCellValueFactory(new PropertyValueFactory("event"));
        logs.getItems().setAll(arr);

    }

    @FXML
    void showTableUser(ActionEvent event) {
        showTableUser();
    }

}
