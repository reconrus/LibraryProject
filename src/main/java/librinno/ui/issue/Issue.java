package main.java.librinno.ui.issue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.Material;
import main.java.librinno.ui.assist.Assist;

import java.sql.SQLException;

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
    void give(ActionEvent event) throws SQLException {
        int book=Integer.parseInt(BookID.getText());
        int patron=Integer.parseInt(PatronID.getText());
        Database db= new Database();

        Alert error;

        switch (Librarian.checkOut(db.getInformationAboutTheUser(patron),book)){
            case -1: error= new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Error");
                error.setContentText("There has been a mistake");
                break;
            case 0: error = new Alert(Alert.AlertType.CONFIRMATION);
                error.setHeaderText("Success");
                error.setContentText("User is in the queue");
                break;
            default: error = new Alert(Alert.AlertType.CONFIRMATION);
                error.setHeaderText("Success");
        }

        error.showAndWait();

        Assist.closeStage(give);
    }

    public void passGUI(Material book){
        BookID.setText(String.valueOf(book.getId()));
        BookID.setEditable(false);
    }
    public void passGUIforPatron(int patronID,int bookID){
        BookID.setText(bookID+"");
        BookID.setEditable(false);
        PatronID.setText(patronID+"");
        PatronID.setEditable(false);
    }

}
