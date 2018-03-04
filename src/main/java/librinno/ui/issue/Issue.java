package main.java.librinno.ui.issue;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.ui.assist.Assist;

import java.sql.SQLException;

public class Issue {

    @FXML
    private JFXButton cancel;

    @FXML
    private JFXButton give;

    @FXML
    private JFXTextField BookID;

    @FXML
    private JFXTextField PatronID;

    @FXML
    void cancel(ActionEvent event) {
        Assist.closeStage(cancel);
    }

    @FXML
    void give(ActionEvent event) throws SQLException {
        int book=Integer.parseInt(BookID.getText());
        int patron=Integer.getInteger(PatronID.getText());
        Database db= new Database();
        Librarian.checkOutBook(db.get_information_about_the_user(patron),book);

        Assist.closeStage(give);
    }

    public void passGUI(Book book){
        BookID.setText(String.valueOf(book.getId()));
        BookID.setEditable(false);
    }
    public void passGUIforPatron(int patronID,int bookID){
        BookID.setText(bookID+"");
        BookID.setEditable(false);
        PatronID.setText(patronID+"");
        PatronID.setEditable(false);
    }

}
