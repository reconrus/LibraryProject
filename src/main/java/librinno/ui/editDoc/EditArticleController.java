package main.java.librinno.ui.editDoc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Article;
import main.java.librinno.model.Book;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

/**
 * Created by CoolHawk on 3/6/2018.
 */
public class EditArticleController {



    @FXML
    private JFXTextField articleID;

    @FXML
    private JFXTextField articleTitle;

    @FXML
    private JFXTextField articleAuthor;

    @FXML
    private JFXTextField articleJournal;

    @FXML
    private JFXTextField articleDate;

    @FXML
    private JFXTextField articleEditors;

    @FXML
    private JFXTextField articleKeyWords;

    @FXML
    private JFXTextField articlePrice;

    @FXML
    private JFXCheckBox articleIsReference;

    @FXML
    private JFXButton cancel;

    private Article article;

    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

    @FXML
    void confirm(ActionEvent event) {
        String title= articleTitle.getText();
        String author = articleAuthor.getText();
        String journal = articleJournal.getText();
        String date = articleDate.getText();
        String editors = articleEditors.getText();
        String keyWords = articleKeyWords.getText();
        String price= articlePrice.getText();
        boolean isReference = articleIsReference.isSelected();

        if ((title.isEmpty())||(author.isEmpty())||(journal.isEmpty())||(date.isEmpty()) || (price.isEmpty())) {
            Assist.error();
        }
        else {
            article.setTitle(title);
            article.setAuthor(author);
            article.setJournal(journal);
            article.setDate(date);
            article.setEditor(editors);
            article.setKeyWords(keyWords);
            article.setPrice(Integer.parseInt(price));
            article.setReference(isReference);

            Librarian.modify_article(article);

            Assist.closeStage(cancel);
        }
    }

    public void passGUI(Article article){
        articleID.setText(article.getId()+"");
        articleID.setEditable(false);
        articleTitle.setText(article.getTitle());
        articleAuthor.setText(article.getAuthor());
        articleJournal.setText(article.getJournal());
        articleDate.setText(article.getDate());
        articleEditors.setText(article.getEditor());
        articleKeyWords.setText(article.getKeyWords());
        articlePrice.setText(String.valueOf(article.getPrice()));
        articleIsReference.setSelected(article.getReference());

        this.article=article;
    }


}
