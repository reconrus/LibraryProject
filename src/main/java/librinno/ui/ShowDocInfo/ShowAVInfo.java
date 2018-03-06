package main.java.librinno.ui.ShowDocInfo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.java.librinno.model.AV;
import main.java.librinno.ui.assist.Assist;

import java.awt.event.ActionEvent;

public class ShowAVInfo {
    @FXML
    private Text title;

    @FXML
    private Text id;

    @FXML
    private Text Author;
    @FXML
    private Text price;

    @FXML
    private Text keyWords;
    @FXML
    private JFXButton close;

    public void passGUI(AV av){
        title.setText(av.getTitle());
        id.setText(""+av.getId());
        Author.setText(av.getTitle());
        price.setText(av.getPrice()+"");
        keyWords.setText(av.getKeyWords());
    }

    @FXML
    public void cancel(){
        Assist.closeStage(close);

    }
}
