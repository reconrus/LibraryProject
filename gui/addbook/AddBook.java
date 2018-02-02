package gui.addbook;

import gui.assist.Assist;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.deploy.association.AssociationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AddBook {

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton save;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    @FXML
    void save(ActionEvent event) {
        String bookTitle= title.getText();
        String bookId= id.getText();
        String bookAuthor= author.getText();
        String bookPublisher= publisher.getText();

        if ((bookAuthor.isEmpty())||(bookId.isEmpty())||(bookPublisher.isEmpty())||(bookTitle.isEmpty())){
            Assist.error();
        }
        else{
            //method for adding books into db
            Assist.closeStage(save);
        }
    }

}
