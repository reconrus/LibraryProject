package main.java.librinno.ui.editDoc;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Book;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

public class EditBookController {

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

    /**
     * closes window without any modification
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) { Assist.closeStage(cancel);}

    /**
     * modifies information about book in database and closes window
     * @param event
     */
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
            bookEd.setEdition(bookEdition);
            bookEd.setKeyWords(bookKeyWords);
            bookEd.setPublisher(bookPublisher);
            bookEd.setBestseller(bestseller);
            bookEd.setReference(reference);
            bookEd.setYear(Integer.parseInt(bookYear));

            Librarian.modifyBook(bookEd);

            Assist.closeStage(cancel);
        }
    }

    /**
     * passes information about the book to EditBook screen so that fields were containing current information
     * @param book
     */
    public void passGUI(Book book){
        name.setText(book.getTitle());
        price.setText(""+book.getPrice());
        author.setText(book.getAuthor());
        edition.setText(""+book.getEdition());
        keyWords.setText(book.getKeyWords());
        publisher.setText(book.getPublisher());
        year.setText(book.getYear()+"");
        isBestseller.setSelected(book.getBestseller());
        isReference.setSelected(book.getReference());
        id.setText(book.getId()+"");
        id.setEditable(false);
        bookEd=book;
    }

}
