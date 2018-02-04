package gui.Menu;

import com.jfoenix.controls.JFXButton;
import gui.assist.Assist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class MenuForPatron {
    @FXML
    private JFXButton searchBook;

    @FXML
    private JFXButton mybooks;

    @FXML
    private JFXButton AllBooks;

    @FXML
    void ShowAllBooks(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../AllBooks/AllBooks.fxml"),"Books",true);
    }

    @FXML
    void Search(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../search/search.fxml"),"Search",true);
    }

    @FXML
    void showmybooks(ActionEvent event) {

    }

}
