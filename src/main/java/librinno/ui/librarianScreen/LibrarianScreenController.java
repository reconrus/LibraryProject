package main.java.librinno.ui.librarianScreen;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
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
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.Register.Register;
import main.java.librinno.ui.addbook.AddBook;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.editDoc.EditDoc;
import main.java.librinno.ui.editPatron.EditPatron;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.LinkedList;

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
    private TableView<Book> tableBook;

    @FXML
    private TableColumn<Book, String> id;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> publisher;

    @FXML
    private TableColumn<Book, Boolean> avaliability;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, Integer> userID;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableColumn<User, String> userPhone;

    @FXML
    private TableColumn<User, String> userAddress;

    @FXML
    private TableColumn<User, String> userType;

    @FXML
    void initialize(){
        showTableUser();
    }

    @FXML
    void showTableUser(){
        userID.setCellValueFactory(new PropertyValueFactory<User,Integer>("card_number"));
        userName.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        userAddress.setCellValueFactory(new PropertyValueFactory<User,String>("adress"));
        userPhone.setCellValueFactory(new PropertyValueFactory<User,String>("Phone_Number"));
        userType.setCellValueFactory(new PropertyValueFactory<User,String>("type"));
        ObservableList<User> list= FXCollections.observableArrayList();
        LinkedList<User> users= Librarian.get_all_users();

        list.addAll(users);

        tableUser.getItems().setAll(list);
    }

    @FXML
    void editPatron() throws IOException {
        User user= tableUser.getSelectionModel().getSelectedItem();
        if (user==null){
            Assist.error();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../editPatron/EditPatron.fxml"));
            Parent parent = loader.load();
            EditPatron reg = (EditPatron) loader.getController();
            reg.passGUI(user);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            showTableUser();
        }
    }

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
    void editDoc() throws IOException {
        Book book= tableBook.getSelectionModel().getSelectedItem();
        if (book==null){
            Assist.error();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../editDoc/EditDoc.fxml"));
            Parent parent = loader.load();
            EditDoc reg = (EditDoc) loader.getController();
            reg.passGUI(book);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            showTableUser();
        }
    }

    @FXML
    void issue(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../issue/issue.fxml"));

    }

    @FXML
    void issueCopy(ActionEvent event) throws IOException{

    }

    @FXML
    void returnDocument(ActionEvent event) throws IOException{

    }

}
