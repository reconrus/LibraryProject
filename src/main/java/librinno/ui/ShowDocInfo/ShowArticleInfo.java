package main.java.librinno.ui.ShowDocInfo;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.java.librinno.model.Article;

public class ShowArticleInfo {
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
    private Text reference;
    @FXML
    private Text editor;
    @FXML
    private Text journal;
    @FXML
    private Text year;

    public void passGUI(Article article){
        title.setText(article.getTitle());
        id.setText(""+article.getId());
        Author.setText(article.getTitle());
        price.setText(article.getPrice()+"");
        keyWords.setText(article.getKeyWords());
        reference.setText(article.getReference()+"");
        year.setText(article.getDate()+"");
        journal.setText(article.getJournal());
        editor.setText(article.getEditor());
    }
}
