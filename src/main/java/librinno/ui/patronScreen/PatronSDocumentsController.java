package main.java.librinno.ui.patronScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.java.librinno.model.Book;
import main.java.librinno.model.Copy;
import main.java.librinno.model.Material;
import main.java.librinno.model.User;
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

    public static void showCopies(LinkedList<Material> copies) {

        id.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Material,String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Material,String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Material,String>("date"));
        status.setCellValueFactory(new PropertyValueFactory<Material,String>("status"));
        ObservableList<Material> list= FXCollections.observableArrayList();

        list.addAll(copies);

        tableCopy.getItems().setAll(list);

    }

    @FXML
    void returnDocument(ActionEvent event){
        Material copy = tableCopy.getSelectionModel().getSelectedItem();
    }
}
