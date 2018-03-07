package main.java.librinno.ui.editDoc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.AV;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

/**
 * Created by CoolHawk on 3/6/2018.
 */
public class EditAVController {


    @FXML
    private JFXTextField avID;

    @FXML
    private JFXTextField avTitle;

    @FXML
    private JFXTextField avAuthor;

    @FXML
    private JFXTextField avPrice;

    @FXML
    private JFXTextField avKeyWords;

    @FXML
    private JFXButton cancel;

    private AV av;

    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

    @FXML
    void confirm(ActionEvent event) {
        String title= avTitle.getText();
        String author = avAuthor.getText();
        String price = avPrice.getText();
        String keyWords = avKeyWords.getText();
        if ((title.isEmpty())||(author.isEmpty())|| (price.isEmpty())) {
            Assist.error();
        }
        else {
            av.setTitle(title);
            av.setAuthor(author);
            av.setKeyWords(keyWords);
            av.setPrice(Integer.parseInt(price));

            Librarian.modifyAV(av);
            Assist.closeStage(cancel);
        }
    }

    public void passGUI(AV av){
        avID.setText(av.getId()+"");
        avID.setEditable(false);
        avTitle.setText(av.getTitle());
        avAuthor.setText(av.getAuthor());
        avKeyWords.setText(av.getKeyWords());
        avPrice.setText(String.valueOf(av.getPrice()));

        this.av=av;
    }
}
