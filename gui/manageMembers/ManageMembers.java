package gui.manageMembers;

import com.jfoenix.controls.JFXButton;
import gui.assist.Assist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ManageMembers {

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> UsernameTable;

    @FXML
    private TableColumn<?, ?> IdTable;

    @FXML
    private TableColumn<?, ?> MailTable;

    @FXML
    private JFXButton cancel;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);

    }

}
