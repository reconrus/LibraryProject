package main.java.librinno.ui.librarianScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.Material;

import java.io.IOException;
import java.util.LinkedList;

public class LibrarianPatronSCopiesController {
    private int userID;
    @FXML
    private TableView<Material> tableDoc;

    @FXML
    private TableColumn<Material, Integer> id;
    @FXML
    private TableColumn<Material, String> author;
    @FXML
    private TableColumn<Material, String> title;
    @FXML
    private TableColumn<Material, String> date;

    @FXML
    void returnDocument(ActionEvent event) throws IOException {
        Material copy= tableDoc.getSelectionModel().getSelectedItem();
        if(copy!=null) {
            Librarian.returnBook(copy.getId());
            showTable();
        }
    }

    void showTable(){
        id.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Material, String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Material, String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Material, String>("returnDate"));
        ObservableList<Material> list= FXCollections.observableArrayList();
        LinkedList<Material> docs= Librarian.get_all_copies_taken_by_user(userID);
        list.addAll(docs);
        tableDoc.getItems().setAll(list);
    }

    void setId(int id){
        userID=id;
        showTable();
    }

    @FXML
    void initialize(){
        showTable();
    }

}
