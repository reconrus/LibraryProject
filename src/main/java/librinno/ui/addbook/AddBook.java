package main.java.librinno.ui.addbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

import java.sql.SQLException;

public class AddBook {

    @FXML
    private Tab bookTab;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField author;

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXTextField edition;

    @FXML
    private JFXTextField year;

    @FXML
    private JFXTextField keyWords;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXTextField amount;

    @FXML
    private JFXCheckBox isBestseller;

    @FXML
    private JFXCheckBox isReference;

    @FXML
    private Tab articleTab;

    @FXML
    private JFXTextField articleTitle;

    @FXML
    private JFXTextField articleJournal;

    @FXML
    private JFXTextField journalDate;

    @FXML
    private JFXTextField journalEditors;

    @FXML
    private JFXTextField journalKeyWords;

    @FXML
    private JFXTextField journalPrice;

    @FXML
    private JFXTextField journalAmount;

    @FXML
    private JFXCheckBox articleIsReference;

    @FXML
    private Tab AVTab;

    @FXML
    private JFXTextField avTitle;

    @FXML
    private JFXTextField avAuthor;

    @FXML
    private JFXTextField avPrice;

    @FXML
    private JFXTextField avKeyWords;

    @FXML
    private JFXTextField avYear;

    @FXML
    private JFXTextField avAmount;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton confirm;

    @FXML
    void confirm(ActionEvent event) throws SQLException {
        if (bookTab.isSelected()) {
            String bookTitle = name.getText();
            String bookPrice = price.getText();
            String bookAuthor = author.getText();
            String bookEdition = edition.getText();
            String bookKeyWords = keyWords.getText();
            String bookPublisher = publisher.getText();
            String bookAmount = amount.getText();
            String bookYear = year.getText();
            Boolean bestseller = isBestseller.isSelected();
            Boolean reference = isReference.isSelected();
            if ((bookAuthor.isEmpty()) || (bookPrice.isEmpty()) || (bookPublisher.isEmpty()) || (bookTitle.isEmpty())
                    || (bookEdition.isEmpty()) || (bookAmount.isEmpty()) || (Integer.parseInt(bookAmount) > 500)) {
                Assist.error();
            } else {
                Librarian.add_book(bookTitle, bookAuthor, bookPublisher, bookEdition, Integer.parseInt(bookPrice), bookKeyWords, bestseller, reference, Integer.parseInt(bookYear), Integer.parseInt(bookAmount));
                Assist.closeStage(cancel);
            }
        }
        else if (articleTab.isSelected()){
            String articleName = articleTitle.getText();
            String bookPrice = price.getText();
            String bookAuthor = author.getText();
            String bookEdition = edition.getText();
            String bookKeyWords = keyWords.getText();
            String bookPublisher = publisher.getText();
            String bookAmount = amount.getText();
            String bookYear = year.getText();
            Boolean bestseller = isBestseller.isSelected();
            Boolean reference = isReference.isSelected();
        }
    }

}
