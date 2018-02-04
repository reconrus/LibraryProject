package gui.Menu;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import gui.assist.Assist;
import java.io.IOException;

public class Menu {

    @FXML
    private JFXButton addbook;

    @FXML
    private JFXButton managemembers;

    @FXML
    private JFXButton search;

    @FXML
    void ShowAllBooks(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../AllBooks/AllBooks.fxml"),"Books",true);
    }

    @FXML
    void addBookButton(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../addbook/AddBook.fxml"), "Add Book",true);
    }

    @FXML
    void gotoManage(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../manageMembers/manageMembers.fxml"),"Members",true);
    }


    @FXML
    void search(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../search/search.fxml"),"Search",true);
    }

}
