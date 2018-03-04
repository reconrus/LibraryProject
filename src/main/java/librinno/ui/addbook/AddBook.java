package main.java.librinno.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

import java.sql.SQLException;

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
    private JFXTextField year;

    @FXML
    private JFXRadioButton isBestseller;

    @FXML
    private JFXRadioButton isReference;

    @FXML
    private JFXButton confirm;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXTextField amount;

    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

    @FXML
    void confirm(ActionEvent event) throws SQLException {
        String bookTitle= name.getText();
        String bookPrice= price.getText();
        String bookAuthor= author.getText();
        String bookEdition= edition.getText();
        String bookKeyWords= keyWords.getText();
        String bookPublisher= publisher.getText();
        String bookAmount= amount.getText();
        String bookYear = year.getText();
        Boolean bestseller= isBestseller.isArmed();
        Boolean reference= isReference.isArmed();
        if ((bookAuthor.isEmpty())||(bookPrice.isEmpty())||(bookPublisher.isEmpty())||(bookTitle.isEmpty())
                || (bookEdition.isEmpty()) || (bookAmount.isEmpty())||(Integer.parseInt(bookAmount)>500)){
            Assist.error();
        }
        else{
            Librarian.add_book(bookTitle,bookAuthor,bookPublisher,Integer.parseInt(bookEdition),Integer.parseInt(bookPrice),bookKeyWords,bestseller, reference, Integer.parseInt(bookYear),Integer.parseInt(bookAmount));
            Assist.closeStage(cancel);
        }
    }

}
