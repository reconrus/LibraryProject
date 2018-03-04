package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.Book;
import main.java.librinno.model.Database;
import main.java.librinno.model.Librarian;
import main.java.librinno.model.User;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.editPatron.EditPatron;
import main.java.librinno.ui.issue.Issue;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class PatronScreenController {


    @FXML
    private JFXButton logout;

    User user;

    @FXML
    private JFXButton copiesButton;

    @FXML
    public Text cardNumber;

    @FXML
    public  Text fullName;

    @FXML
    private  Text phone;

    @FXML
    private Text address;

    @FXML
    private Text bookCount;

    @FXML
    private Text type;
    @FXML
    private TableView<Book> tableBook;

    @FXML
    private TableColumn<Book, Integer> id;

    @FXML
    private TableColumn<Book, String> author;

    @FXML
    private TableColumn<Book, String> title;

    @FXML
    private TableColumn<Book, String> publisher;

    @FXML
    private TableColumn<Book, Integer> avaliability;

    @FXML
    void showTableDocuments(){
        id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        title.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        publisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
        avaliability.setCellValueFactory(new PropertyValueFactory<Book, Integer>("number"));

        ObservableList<User> list= FXCollections.observableArrayList();
        ArrayList<Book> books = Librarian.get_all_books();


        tableBook.getItems().setAll(books);
    }

    @FXML
    void initialize(){
        showTableDocuments();
    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
    }

    public void setUserInfo(User patron){
        cardNumber.setText(String.valueOf(patron.get_card_number()));
        fullName.setText(patron.get_name());
        phone.setText(patron.get_number());
        address.setText(patron.get_adress());
        type.setText(patron.get_type());
        user=patron;
        bookCount.setText(""+Librarian.get_number_of_all_copies_taken_by_user(patron.getCard_number()));
    }

    @FXML
    private void showCopies(ActionEvent event)throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));
        Parent parent = loader.load();

        PatronSDocumentsController controller = loader.getController();
        controller.setId(user.get_card_number());

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @FXML
    private void reserve(ActionEvent event) throws IOException {
        Book book= tableBook.getSelectionModel().getSelectedItem();
        if(book == null){
            Assist.error();
            return;
        }

        Boolean flag = Librarian.checkOutBook(user,book.getId());
        if (flag){
            Alert error= new Alert(Alert.AlertType.CONFIRMATION);
            error.setHeaderText("Success");
            error.showAndWait();
        }
        else{
            Alert error= new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error");
            error.setContentText("There has been a mistake");
            error.showAndWait();
        }
        showTableDocuments();
    }
}
