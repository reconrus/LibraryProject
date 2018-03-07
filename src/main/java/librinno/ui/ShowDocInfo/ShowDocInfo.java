package main.java.librinno.ui.ShowDocInfo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.java.librinno.model.*;
import main.java.librinno.ui.assist.Assist;

public class ShowDocInfo {

    @FXML
    private Text title;

    @FXML
    private Text id;

    @FXML
    private Text Author;

    @FXML
    private Text publisher;

    @FXML
    private Text edition;

    @FXML
    private Text price;

    @FXML
    private Text keyWords;

    @FXML
    private Text bestseller;

    @FXML
    private Text reference;

    @FXML
    private Text year;

    @FXML
    private JFXButton close;

    void passGUI(Book book){
        title.setText(book.getTitle());
        id.setText(""+book.getId());
        Author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        keyWords.setText(book.getKeyWords());
        bestseller.setText(book.getBestseller()+"");
        reference.setText(book.getReference()+"");
        year.setText(book.getYear()+"");
        price.setText(book.getPrice()+"");
        edition.setText(book.getEdition());
    }


    public void passGUI(Material material){
        String s=material.getType();
        if (s.equals("AV")) passGUI(Librarian.avById(material.getId()));
        else if (s.equals("Book")) passGUI(Librarian.bookByID(material.getId()));
        else passGUI(Librarian.articleById(material.getId()));
    }

    @FXML
    void cancel(javafx.event.ActionEvent event) { Assist.closeStage(close);}


}
