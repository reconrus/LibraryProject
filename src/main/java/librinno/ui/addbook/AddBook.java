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
    private JFXCheckBox isBestseller;

    @FXML
    private JFXCheckBox isReference;

    @FXML
    private Tab articleTab;

    @FXML
    private JFXTextField articleTitle;
    @FXML
    private JFXTextField articleAuthors;

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
    private JFXTextField articleAmount;

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
    private JFXTextField avAmount;
    private JFXButton confirm;

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXTextField amount;

    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

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
        } else if (articleTab.isSelected()) {
            String title = articleTitle.getText();
            String author = articleAuthors.getText();
            String journal = articleJournal.getText();
            String date = articleDate.getText();
            String editors = articleEditors.getText();
            String keyWords = articleKeyWords.getText();
            String price = articlePrice.getText();
            boolean isReference = articleIsReference.isSelected();
            String amount = articleAmount.getText();
            if ((author.isEmpty()) || (price.isEmpty()) || (journal.isEmpty()) || (title.isEmpty())
                    || (editors.isEmpty()) || (amount.isEmpty()) || (Integer.parseInt(amount) > 500)) {
                Assist.error();
            } else {
                Librarian.add_article(title, author, Integer.parseInt(price), keyWords, isReference, journal, editors, date, Integer.parseInt(amount));
                Assist.closeStage(cancel);
            }
        } else {
            String titleAV = avTitle.getText();
            String authorAV = avAuthor.getText();
            String keyWordsAV = avKeyWords.getText();
            String priceAV = avPrice.getText();
            String amountAV = avAmount.getText();
            if ((authorAV.isEmpty()) || (priceAV.isEmpty()) || (titleAV.isEmpty())
                    || (amountAV.isEmpty()) || (Integer.parseInt(amountAV) > 500)) {
                Assist.error();
            } else {
                Librarian.add_AV(titleAV, authorAV, Integer.parseInt(priceAV), keyWordsAV, Integer.parseInt(amountAV));
                Assist.closeStage(cancel);
            }
        }
    }

    @FXML
    void cancel(){
        Assist.closeStage(cancel);
    }
}

