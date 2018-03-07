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
import main.java.librinno.ui.ShowDocInfo.ShowAVInfo;
import main.java.librinno.ui.ShowDocInfo.ShowArticleInfo;
import main.java.librinno.ui.ShowDocInfo.ShowDocInfo;
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
    private TableColumn<Material, String> copyType;

    @FXML
    private TableColumn<Material, Integer> avaliability;

    @FXML
    void showTableDocuments(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        author.setCellValueFactory(new PropertyValueFactory("author"));
        title.setCellValueFactory(new PropertyValueFactory("title"));
        copyType.setCellValueFactory(new PropertyValueFactory("type"));
        avaliability.setCellValueFactory(new PropertyValueFactory("numberAvailable"));
        ArrayList<Material> books = Librarian.getAllBooks();
        books.addAll(Librarian.getAllArticles());
        books.addAll(Librarian.getAllAV());
        tableBook.getItems().setAll(books);
    }

    @FXML
    void showBookInfo() throws IOException {
        Material material = tableBook.getSelectionModel().getSelectedItem();
        String mat=material.getType();
        if (mat.equals("AV")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/ShowDocInfo/ShowAVInfo.fxml"));
            Parent parent = loader.load();
            ShowAVInfo reg = loader.getController();
            reg.passGUI(Librarian.avById(material.getId()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }else if (mat.equals("Book")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/ShowDocInfo/ShowDocInfo.fxml"));
            Parent parent = loader.load();
            ShowDocInfo reg = loader.getController();
            reg.passGUI(Librarian.bookByID(material.getId()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/ShowDocInfo/ShowArticleInfo.fxml"));
            Parent parent = loader.load();
            ShowArticleInfo reg = loader.getController();
            reg.passGUI(Librarian.articleById(material.getId()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }
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
        cardNumber.setText(String.valueOf(patron.getCard_Number()));
        fullName.setText(patron.getName());
        phone.setText(patron.getPhoneNumber());
        address.setText(patron.getAdress());
        type.setText(patron.getType());
        user=patron;
        bookCount.setText(""+Librarian.getNumberOfAllCopiesTakenByUser(patron.getCard_number()));
    }

    @FXML
    private void showCopies(ActionEvent event)throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/patronScreen/PatronSDocuments.fxml"));
        Parent parent = loader.load();

        PatronSDocumentsController controller = loader.getController();
        controller.setId(user.getCard_Number());

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.showAndWait();

        showTableDocuments();
        bookCount.setText(""+Librarian.getNumberOfAllCopiesTakenByUser(user.getCard_number()));
    }

    @FXML
    void refresh(){
        showTableDocuments();
        bookCount.setText(""+Librarian.getNumberOfAllCopiesTakenByUser(user.getCard_number()));
    }

    @FXML
    private void reserve(ActionEvent event) throws IOException {
        Material book= tableBook.getSelectionModel().getSelectedItem();
        if(book == null){
            Assist.error();
            return;
        }

        Boolean flag = Librarian.checkOut(user,book.getId());
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
