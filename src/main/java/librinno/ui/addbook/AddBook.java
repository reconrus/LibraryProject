package main.java.librinno.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.ui.assist.Assist;

public class AddBook {

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField edition;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXTextField keyWords;

    @FXML
    private JFXRadioButton isBestseller;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXTextField amount;

    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

    @FXML
    void confirm(ActionEvent event) {
        String bookTitle= name.getText();
        String bookPrice= price.getText();
        String bookAuthor= author.getText();
        String bookEdition= edition.getText();
        String bookKeyWords= keyWords.getText();
        String bookPublisher= publisher.getText();
        String bookAmount= amount.getText();
        Boolean bestseller= isBestseller.isArmed();
        if ((bookAuthor.isEmpty())||(bookPrice.isEmpty())||(bookPublisher.isEmpty())||(bookTitle.isEmpty())){
            Assist.error();
        }
        else{
            Database db= new Database();
            Book book= new Book(bookTitle,bookAuthor,bookPublisher,Integer.parseInt(bookEdition),Integer.parseInt(bookPrice),bookKeyWords,bestseller,999,false);
            db.book_creation(book);
            Assist.closeStage(cancel);
        }
    }

}
