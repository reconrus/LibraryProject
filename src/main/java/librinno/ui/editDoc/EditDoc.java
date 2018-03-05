package main.java.librinno.ui.editDoc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javassist.compiler.ast.Keyword;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

public class EditDoc {

    @FXML
    private JFXTextField publisher;

    @FXML
    private JFXTextField id;

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
    private JFXButton confirm;

    @FXML
    private JFXButton cancel;
    
    @FXML
    private JFXCheckBox isReference;

    private Book bookEd;

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
        String bookYear= year.getText();
        Boolean bestseller= isBestseller.isSelected();
        Boolean reference= isReference.isSelected();

        if ((bookAuthor.isEmpty())||(bookPrice.isEmpty())||(bookPublisher.isEmpty())||(bookTitle.isEmpty())) {
            Assist.error();
        }
        else {
            bookEd.setTitle(bookTitle);
            bookEd.setPrice(Integer.parseInt(bookPrice));
            bookEd.setAuthor(bookAuthor);
            bookEd.setEdition(Integer.parseInt(bookEdition));
            bookEd.setKeyWords(bookKeyWords);
            bookEd.setPublisher(bookPublisher);
            bookEd.setIs_bestseller(bestseller);
            bookEd.setReference(reference);
            bookEd.setYear(Integer.parseInt(bookYear));

            Librarian.modify_book(bookEd);

            Assist.closeStage(cancel);
        }
    }

    public void passGUI(Book book){
        name.setText(book.getTitle());
        price.setText(""+book.getPrice());
        author.setText(book.getAuthor());
        edition.setText(""+book.getEdition());
        keyWords.setText(book.getKeyWords());
        publisher.setText(book.getPublisher());
        year.setText(book.getYear()+"");
        isBestseller.setSelected(book.isIs_bestseller());
        isReference.setSelected(book.isReference());
        id.setText(book.getId()+"");
        id.setEditable(false);
        bookEd=book;
    }

}
