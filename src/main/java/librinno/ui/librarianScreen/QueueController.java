package main.java.librinno.ui.librarianScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;

import java.util.ArrayList;
import java.util.LinkedList;

public class QueueController {

    private int materialID;

    @FXML
    private TableView<User> tableQueue;

    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, String> reservationDate;
    @FXML
    private TableColumn<User, Boolean> isNotified;

    void showQueue(){
        id.setCellValueFactory(new PropertyValueFactory<>("card_number"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        reservationDate.setCellValueFactory(new PropertyValueFactory<>("queue_date"));
        isNotified.setCellValueFactory(new PropertyValueFactory<>("isNotified"));
        ObservableList<User> list= FXCollections.observableArrayList();
        ArrayList<User> users = Librarian.getQueue(materialID);
        list.addAll(users);
        tableQueue.getItems().setAll(list);
    }

    public void setID(int id){
        materialID = id;
        showQueue();
    }

}
