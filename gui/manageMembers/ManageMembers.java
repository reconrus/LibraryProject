package gui.manageMembers;

import com.jfoenix.controls.JFXButton;
import gui.assist.Assist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

public class ManageMembers {

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private TableColumn<?, ?> phone;

    @FXML
    private TableColumn<?, ?> profession;

    @FXML
    private JFXButton addMember;

    @FXML
    private JFXButton cancel;

    @FXML
    void addMember(ActionEvent event) throws IOException {
        Assist.loadStage(getClass().getResource("../login/register/register.fxml"),"Adding Member", true);
    }

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);

    }

}
