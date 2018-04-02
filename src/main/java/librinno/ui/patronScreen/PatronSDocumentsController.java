package main.java.librinno.ui.patronScreen;

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
import main.java.librinno.model.User;

import java.util.LinkedList;

public class PatronSDocumentsController {
    private User user;
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
    private TableColumn<Material, String> fine;

    @FXML
    void renewCopy(ActionEvent event) {
        Material copy= tableDoc.getSelectionModel().getSelectedItem();
        if(copy!=null){

            if (Librarian.renew(user, copy.getId())){
                Alert error= new Alert(Alert.AlertType.CONFIRMATION);
                error.setHeaderText("Success");
                error.setContentText("Your request has been accepted");
                error.showAndWait();
            }
            else{
                Alert error= new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Error");
                error.setContentText("You are not able to renew");
                error.showAndWait();
            }

            showTable();
        }
    }

    @FXML
    void returnCopy(){
        Material copy= tableDoc.getSelectionModel().getSelectedItem();
        if(copy!=null){

            if (Librarian.requestReturnBook(copy.getId())){
                Alert error= new Alert(Alert.AlertType.CONFIRMATION);
                error.setHeaderText("Success");
                error.setContentText("Your request has been accepted");
                error.showAndWait();
            }
            else{
                Alert error= new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Error");
                error.setContentText("There has been a mistake");
                error.showAndWait();
            }

            showTable();
        }
    }

    void showTable(){
        id.setCellValueFactory(new PropertyValueFactory<Material, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Material, String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Material, String>("title"));
        date.setCellValueFactory(new PropertyValueFactory<Material, String>("returnDate"));
        fine.setCellValueFactory(new PropertyValueFactory<Material, String>("fine"));

        ObservableList<Material> list= FXCollections.observableArrayList();
        LinkedList<Material> docs= Librarian.getAllCopiesTakenByUser(user.getCard_number());

        list.addAll(docs);
        tableDoc.getItems().setAll(list);
    }

    void setUser(User user){
        this.user = user;
        showTable();
    }

}
