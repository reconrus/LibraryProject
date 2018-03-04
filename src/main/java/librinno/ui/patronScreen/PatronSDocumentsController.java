package main.java.librinno.ui.patronScreen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.librinno.model.Book;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.Material;

import java.util.LinkedList;

public class PatronSDocumentsController {
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
    void returnCopy(){
        Material copy= tableDoc.getSelectionModel().getSelectedItem();

    }

    void showTable(){
        id.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Material, String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Material, String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Material, String>("date"));

        ObservableList<Material> list= FXCollections.observableArrayList();
        LinkedList<Material> docs= Librarian.get_all_copies_taken_by_user(userID);

        list.addAll(docs);
        tableDoc.getItems().setAll(list);

    }




    void setId(int id){
        userID=id;
        showTable();
    }

}
