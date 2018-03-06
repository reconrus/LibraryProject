package main.java.librinno.ui.patronScreen;

import com.jfoenix.controls.JFXButton;
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
import main.java.librinno.model.*;
import main.java.librinno.ui.assist.Assist;
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
    private TableView<Material> tableBook;

    @FXML
    private TableColumn<Material, Integer> id;

    @FXML
    private TableColumn<Material, String> author;

    @FXML
    private TableColumn<Material, String> title;

    @FXML
    private TableColumn<Material, String> publisher;

    @FXML
    private TableColumn<Material, Integer> avaliability;

    @FXML
    void showTableDocuments(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        author.setCellValueFactory(new PropertyValueFactory("author"));
        title.setCellValueFactory(new PropertyValueFactory("title"));
        publisher.setCellValueFactory(new PropertyValueFactory("publisher"));
        avaliability.setCellValueFactory(new PropertyValueFactory("number"));
        ArrayList<Material> books = Librarian.get_all_books();


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
        stage.showAndWait();

        showTableDocuments();
        bookCount.setText(""+Librarian.get_number_of_all_copies_taken_by_user(user.getCard_number()));
    }

    @FXML
    void refresh(){
        showTableDocuments();
        bookCount.setText(""+Librarian.get_number_of_all_copies_taken_by_user(user.getCard_number()));
    }

    @FXML
    private void reserve(ActionEvent event) throws IOException {
        Material book= tableBook.getSelectionModel().getSelectedItem();
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
        refresh();
    }
}
