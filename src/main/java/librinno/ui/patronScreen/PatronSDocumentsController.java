package main.java.librinno.ui.patronScreen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import main.java.librinno.ui.assist.Assist;

import java.io.IOException;

/**
 *
 */
public class PatronSDocumentsController {

    public static void showCopies(MouseEvent event, int id) throws IOException{
        Assist.loadStage(event.getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));
    }

    @FXML
    void returnDocument(ActionEvent event){

    }
}
