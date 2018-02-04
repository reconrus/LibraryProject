package gui.AllBooks;

import com.jfoenix.controls.JFXButton;
import gui.assist.Assist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AllBooks {

    @FXML
    private TableView<?> BookTable;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> Title;

    @FXML
    private TableColumn<?, ?> author;

    @FXML
    private TableColumn<?, ?> Avaliability;

    @FXML
    private TableColumn<?, ?> publisher;

    @FXML
    private TableColumn<?, ?> price;

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

}
