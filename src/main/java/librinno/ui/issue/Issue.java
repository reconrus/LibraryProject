package main.java.librinno.ui.issue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.ui.assist.Assist;

public class Issue {

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton give;

    @FXML
    private JFXTextField BookID;

    @FXML
    private JFXTextField PatronID;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    @FXML
    void give(ActionEvent event) {
        int book=Integer.parseInt(BookID.getText());
        int patron=Integer.getInteger(PatronID.getText());


        Assist.closeStage(give);
    }

}
