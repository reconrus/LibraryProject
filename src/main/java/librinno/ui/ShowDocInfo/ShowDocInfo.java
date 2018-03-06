package main.java.librinno.ui.ShowDocInfo;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.java.librinno.model.*;
import main.java.librinno.ui.assist.Assist;

import java.awt.event.ActionEvent;

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
    private Text editor;
    @FXML
    private Text journal;

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
        bestseller.setText(book.isIs_bestseller()+"");
        reference.setText(book.isReference()+"");
        year.setText(book.getYear()+"");
        price.setText(book.getPrice()+"");
        edition.setText(book.getEdition());
    }
    void passGUI(AV av){
        title.setText(av.getTitle());
        id.setText(""+av.getId());
        Author.setText(av.getTitle());
        price.setText(av.getPrice()+"");
        keyWords.setText(av.getKeyWords());
    }
    void passGUI(Article article){
        title.setText(article.getTitle());
        id.setText(""+article.getId());
        Author.setText(article.getTitle());
        price.setText(article.getPrice()+"");
        keyWords.setText(article.getKeyWords());
        reference.setText(article.getReference()+"");
        year.setText(article.getDate()+"");
        journal.setText(article.getJournal());
        journal.setText(article.getEditor());
    }

    public void passGUI(Material material){
        if (material.getType().equals("AV")){
            passGUI(Librarian.bookByID(material.getId()));
        }
    }

    @FXML
    public void cancel(ActionEvent event){
        Assist.closeStage(close);
    }


}
