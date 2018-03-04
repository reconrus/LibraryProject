package main.java.librinno.ui.patronScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.java.librinno.model.*;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;
import java.util.LinkedList;


public class PatronSDocumentsController {

    @FXML
    private static TableView<Material> tableCopy;

    @FXML
    private static TableColumn<Material, Integer> id;

    @FXML
    private static TableColumn<Material, String> author;

    @FXML
    private static TableColumn<Material, String> title;

    @FXML
    private static TableColumn<Material, String> date;

    @FXML
    private static TableColumn<Material, String> status;

    private int userId;

    public void showCopies() {

        id.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Material,String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Material,String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Material,String>("date"));
        status.setCellValueFactory(new PropertyValueFactory<Material,String>("status"));
        ObservableList<Material> list= FXCollections.observableArrayList();

        LinkedList<Material> copies = Librarian.get_all_copies_taken_by_user(userId);

        list.addAll(copies);

        tableCopy.getItems().setAll(list);

    }

    @FXML
    void returnDocument(ActionEvent event){
        Material copy = tableCopy.getSelectionModel().getSelectedItem();
    }

    public void setId(int id){
        userId = id;
    }


}
