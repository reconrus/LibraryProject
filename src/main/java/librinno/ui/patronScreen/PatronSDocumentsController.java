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
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;
import java.util.LinkedList;

/**
 *
 */
public class PatronSDocumentsController {

    @FXML
    private static TableView<Copy> tableCopy;

    @FXML
    private static TableColumn<Copy, String> author;

    @FXML
    private static TableColumn<Copy, String> title;

    @FXML
    private static TableColumn<Copy, String> date;

    public static void showCopies(LinkedList<Copy> copies) {

        author.setCellValueFactory(new PropertyValueFactory<Copy,String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Copy,String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Copy,String>("date"));
        ObservableList<Copy> list= FXCollections.observableArrayList();

        list.addAll(copies);

        tableCopy.getItems().setAll(list);

    }

    @FXML
    void returnDocument(ActionEvent event){
        Copy copy = tableCopy.getSelectionModel().getSelectedItem();
    }
}
