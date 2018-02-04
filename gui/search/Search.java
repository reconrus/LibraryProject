package gui.search;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gui.assist.Assist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Search {

    @FXML
    private JFXTextField SearchField;

    @FXML
    private TableView<?> BookTable;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> Title;

    @FXML
    private TableColumn<?, ?> author;

    @FXML
    private TableColumn<?, ?> publisher;

    @FXML
    private TableColumn<?, ?> price;

    @FXML
    private TableColumn<?, ?> Avaliability;

    @FXML
    private TableColumn<?, ?> type;

    @FXML
    private TableColumn<?, ?> KeyWords;

    @FXML
    private JFXButton Cancel;

    @FXML
    void Cancel(ActionEvent event) {
        Assist.closeStage(Cancel);
    }

    @FXML
    void Search(ActionEvent event) {
        BookTable.setVisible(true);
    }

}
