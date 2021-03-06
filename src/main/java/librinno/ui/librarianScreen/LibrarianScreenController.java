package main.java.librinno.ui.librarianScreen;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.*;
import main.java.librinno.ui.ShowDocInfo.ShowAVInfo;
import main.java.librinno.ui.ShowDocInfo.ShowArticleInfo;
import main.java.librinno.ui.ShowDocInfo.ShowDocInfo;
import main.java.librinno.ui.addPatron.AddPatronController;
import main.java.librinno.ui.assist.Assist;
import main.java.librinno.ui.editDoc.EditAVController;
import main.java.librinno.ui.editDoc.EditArticleController;
import main.java.librinno.ui.editDoc.EditBookController;
import main.java.librinno.ui.editPatron.EditPatron;
import main.java.librinno.ui.issue.Issue;
import main.java.librinno.ui.patronScreen.PatronScreenController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class LibrarianScreenController {


    @FXML
    private JFXButton logout;

    Librarian librarian;

    @FXML
    private JFXButton addDoc;

    @FXML
    private JFXButton deleteDoc;

    @FXML
    private JFXButton issue;

    @FXML
    private JFXButton editDoc;

    @FXML
    private TableView<Material> tableBook;

    @FXML
    private TableColumn<Material, Integer> id;

    @FXML
    private TableColumn<Material, String> author;

    @FXML
    private TableColumn<Material, String> title;

    @FXML
    private TableColumn<Material, Integer> avaliability;

    @FXML
    private TableColumn<Material, Integer> total;

    @FXML
    private TableColumn<Material, String> bookType;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TableColumn<User, Integer> userID;

    @FXML
    private TableColumn<User, String> userName;

    @FXML
    private TableColumn<User, String> userPhone;

    @FXML
    private TableColumn<User, String> userAddress;

    @FXML
    private TableColumn<User, String> userType;

    @FXML
    private TableView<Material> tableCopy;

    @FXML
    private TableColumn<Material, Integer> idCopy;

    @FXML
    private TableColumn<Material, String> authorCopy;

    @FXML
    private TableColumn<Material, String> titleCopy;

    @FXML
    private TableColumn<Material, String> statusCopy;

    @FXML
    private TableColumn<Material, Integer> issuedTo;

    @FXML
    private TableColumn<Material, Integer> fine;

    @FXML
    private JFXComboBox<String> searchSection = new JFXComboBox<>();

    @FXML
    private JFXCheckBox searchBooks;

    @FXML
    private JFXCheckBox searchArticles;

    @FXML
    private JFXCheckBox searchAVs;

    @FXML
    private JFXCheckBox searchBestsellers;

    @FXML
    private JFXCheckBox searchNotReferences;

    @FXML
    private JFXCheckBox searchAvailable;

    @FXML
    private JFXTextField searchField;

    public void setLibrarianInfo(Librarian librarian){
        this.librarian=librarian;
    }


    //TODO Delete librarians from this table
    @FXML
    void showTableUser(){
        userID.setCellValueFactory(new PropertyValueFactory<User,Integer>("card_number"));
        userName.setCellValueFactory(new PropertyValueFactory<User,String>("name"));
        userAddress.setCellValueFactory(new PropertyValueFactory<User,String>("adress"));
        userPhone.setCellValueFactory(new PropertyValueFactory<User,String>("phoneNumber"));
        userType.setCellValueFactory(new PropertyValueFactory<User,String>("type"));
        ObservableList<User> list= FXCollections.observableArrayList();
        LinkedList<User> users= Librarian.getAllUsers();

        list.addAll(users);

        tableUser.getItems().setAll(list);
    }

    @FXML
    void showTableDocuments(){
        id.setCellValueFactory(new PropertyValueFactory("id"));
        author.setCellValueFactory(new PropertyValueFactory("author"));
        title.setCellValueFactory(new PropertyValueFactory("title"));
        avaliability.setCellValueFactory(new PropertyValueFactory("numberAvailable"));
        total.setCellValueFactory(new PropertyValueFactory("totalNumber"));
        bookType.setCellValueFactory(new PropertyValueFactory("type"));

        ObservableList<Material> list= FXCollections.observableArrayList();
        list.addAll(Librarian.getAllArticles());
        list.addAll(Librarian.getAllAV());
        list.addAll(Librarian.getAllBooks());
        tableBook.getItems().setAll(list);
    }

    @FXML
    void search() throws SQLException {
        boolean haveCriteria = searchBestsellers.isSelected() || searchNotReferences.isSelected() || searchAvailable.isSelected();
        boolean allTypes = !searchBooks.isSelected() && !searchArticles.isSelected() && !searchAVs.isSelected();

        boolean searchByAll = searchSection.getSelectionModel().getSelectedItem().equals("All");
        boolean searchByTitle = searchSection.getSelectionModel().getSelectedItem().equals("Title");
        boolean searchByAuthor = searchSection.getSelectionModel().getSelectedItem().equals("Author");
        boolean searchByKeywords = searchSection.getSelectionModel().getSelectedItem().equals("Keywords");
        ArrayList<Material> materials;

        if(allTypes)
            if(searchByAll) materials = Search.materialByWordWithCriteria(searchField.getText(), true, true, true, true, true, true, searchBestsellers.isSelected(), !searchNotReferences.isSelected(), searchAvailable.isSelected());
            else materials = Search.materialByWordWithCriteria(searchField.getText(), true, true, true, searchByTitle, searchByAuthor, searchByKeywords, searchBestsellers.isSelected(), !searchNotReferences.isSelected(), searchAvailable.isSelected());

        else if(searchByAll) materials = Search.materialByWordWithCriteria(searchField.getText(), searchBooks.isSelected(), searchArticles.isSelected(), searchAVs.isSelected(), true, true, true, searchBestsellers.isSelected(), !searchNotReferences.isSelected(), searchAvailable.isSelected());
        else materials = Search.materialByWordWithCriteria(searchField.getText(), searchBooks.isSelected(), searchArticles.isSelected(), searchAVs.isSelected(), searchByTitle, searchByAuthor, searchByKeywords, searchBestsellers.isSelected(), !searchNotReferences.isSelected(), searchAvailable.isSelected());

        id.setCellValueFactory(new PropertyValueFactory("id"));
        author.setCellValueFactory(new PropertyValueFactory("author"));
        title.setCellValueFactory(new PropertyValueFactory("title"));
        avaliability.setCellValueFactory(new PropertyValueFactory("numberAvailable"));
        total.setCellValueFactory(new PropertyValueFactory("totalNumber"));
        bookType.setCellValueFactory(new PropertyValueFactory("type"));

        tableBook.getItems().setAll(materials);
    }

    @FXML
    private void clearCriteria(){
        searchBooks.setSelected(false);
        searchArticles.setSelected(false);
        searchAVs.setSelected(false);
        searchBestsellers.setSelected(false);
        searchNotReferences.setSelected(false);
        searchAvailable.setSelected(false);
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
            stage.setResizable(false);
            stage.showAndWait();
        }else if (mat.equals("Book")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/ShowDocInfo/ShowDocInfo.fxml"));
            Parent parent = loader.load();
            ShowDocInfo reg = loader.getController();
            reg.passGUI(Librarian.bookByID(material.getId()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/ShowDocInfo/ShowArticleInfo.fxml"));
            Parent parent = loader.load();
            ShowArticleInfo reg = loader.getController();
            reg.passGUI(Librarian.articleById(material.getId()));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.showAndWait();
        }
    }

    @FXML
    void showTableCopy(){
        idCopy.setCellValueFactory(new PropertyValueFactory("id"));
        authorCopy.setCellValueFactory(new PropertyValueFactory("author"));
        titleCopy.setCellValueFactory(new PropertyValueFactory("title"));
        statusCopy.setCellValueFactory(new PropertyValueFactory("status"));
        issuedTo.setCellValueFactory(new PropertyValueFactory("userId"));
        fine.setCellValueFactory(new PropertyValueFactory("fine"));


        ObservableList<Material> list= FXCollections.observableArrayList();
        LinkedList<Material> docs= Librarian.getAllCopies();

        list.addAll(docs);
        tableCopy.getItems().setAll(list);

    }

    @FXML
    void editPatron() throws IOException {
        User user= tableUser.getSelectionModel().getSelectedItem();
        if (user==null){
            Assist.error();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/editPatron/EditPatron.fxml"));
            Parent parent = loader.load();
            EditPatron reg = (EditPatron) loader.getController();
            reg.passGUI(user);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.showAndWait();
            showTableUser();
        }
    }

    @FXML
    void addDoc(ActionEvent event) throws IOException {
        if (librarian.getPrivileges().equals("Priv1")){
            Assist.error("Access Denied", "Your privilege level is insufficient.");
            return;
        }

        Assist.loadStageWait(getClass().getResource("/main/java/librinno/ui/addBook/AddBook.fxml"));
        showTables();
    }

    @FXML
    void addPatron(ActionEvent event) throws IOException {
        if (librarian.getPrivileges().equals("Priv1")){
            Assist.error("Access Denied", "Your privilege level is insufficient.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/addPatron/AddPatron.fxml"));
        Parent parent= loader.load();
        AddPatronController reg = loader.getController();
        reg.setLibrarian(this.librarian);

        Stage stage= new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.showAndWait();

        showTableUser();
    }

    @FXML
    void addCopy(){
        Material material= tableBook.getSelectionModel().getSelectedItem();
        librarian.addCopiesOfMaterial(material.getId(),1);
        showTables();
    }
    @FXML
    void deletePatron(ActionEvent event) {
        if (!librarian.getPrivileges().equals("Priv3")){
            Assist.error("Access Denied", "Your privilege level is insufficient.");
            return;
        }
        User user= tableUser.getSelectionModel().getSelectedItem();
        if(user!=null) {
            librarian.deleteUserById(user.card_number);
            showTableUser();
        }
    }

    @FXML
    void deleteDoc(ActionEvent event) {
        if (!librarian.getPrivileges().equals("Priv3")){
            Assist.error("Access Denied", "Your privilege level is insufficient.");
            return;
        }
        Material book= tableBook.getSelectionModel().getSelectedItem();
        if(book != null) {
            librarian.deleteDoc(book);
            showTables();
        }
    }

    @FXML
    void editDoc() throws IOException {
        Material material = tableBook.getSelectionModel().getSelectedItem();
        if (material==null){
            Assist.error();
        }else {
            FXMLLoader loader;
            Parent parent;
            if(material.getType().equals("Article")){
                Article article = (Article) tableBook.getSelectionModel().getSelectedItem();
                loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/editDoc/EditArticle.fxml"));
                parent = loader.load();
                EditArticleController reg = loader.getController();
                reg.passGUI(article);
            }
            else if(material.getType().equals("AV")){
                AV av = (AV) tableBook.getSelectionModel().getSelectedItem();
                loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/editDoc/EditAV.fxml"));
                parent = loader.load();
                EditAVController reg = loader.getController();
                reg.passGUI(av);
            }
            else { //Book
                Book book = (Book) tableBook.getSelectionModel().getSelectedItem();
                loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/editDoc/EditBook.fxml"));
                parent = loader.load();
                EditBookController reg = loader.getController();
                reg.passGUI(book);
            }
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.showAndWait();
            showTables();
        }
    }

    @FXML
    void issue(ActionEvent event) throws IOException {
        Material book= tableBook.getSelectionModel().getSelectedItem();

        if (book==null){
            Assist.loadStageWait(getClass().getResource("/main/java/librinno/ui/issue/Issue.fxml"));
            showTables();
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/issue/Issue.fxml"));
            Parent parent = loader.load();
            Issue reg = (Issue) loader.getController();
            reg.passGUI(book);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.setResizable(false);
            stage.showAndWait();
            showTables();
        }

    }
    @FXML
    void showPatronCopies() throws IOException {
        User user= tableUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/librarianScreen/LibrarianPatronSDocuments.fxml"));
        Parent parent = loader.load();

        LibrarianPatronSCopiesController controller = loader.getController();
        controller.setId(user.getCard_number());

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.showAndWait();
        showTables();
    }

    @FXML
    void returnDocument(ActionEvent event) throws IOException{
        Material copy= tableCopy.getSelectionModel().getSelectedItem();
        if(copy!=null) {
            Librarian.returnBook(copy.getId());
            showTables();
        }
    }

    @FXML
    void deleteCopy (ActionEvent event) throws IOException{
        Material copy= tableCopy.getSelectionModel().getSelectedItem();
        if(copy!=null) {
            librarian.deleteOneCopy(copy.getId());
            showTables();
        }
    }

    @FXML
    private void logoutAction(ActionEvent event) throws IOException {
        Assist.closeStage(logout);
        Assist.loadStage(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));
    }

    @FXML
    private void outstandingRequest(){
        Material doc= tableBook.getSelectionModel().getSelectedItem();
        librarian.outstandingRequest(doc.getId());
        Alert error = new Alert(Alert.AlertType.CONFIRMATION);
        error.setHeaderText("Success");
        error.showAndWait();
        showTables();
    }

    @FXML
    private void showQueue() throws IOException {
        Material material = tableBook.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/java/librinno/ui/librarianScreen/Queue.fxml"));
        Parent parent = loader.load();

        QueueController controller = loader.getController();
        controller.setID(material.getId());

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setResizable(false);
        stage.showAndWait();
    }

    @FXML
    private void showTables(){
        showTableDocuments();
        showTableCopy();
    }

    @FXML
    void initialize(){
        showTableUser();
        showTableDocuments();
        showTableCopy();
        setSearchSectionsBox();
    }

    private void setSearchSectionsBox(){
        searchSection.getItems().add("All");
        searchSection.getItems().add("Title");
        searchSection.getItems().add("Author");
        searchSection.getItems().add("Keywords");
        searchSection.getSelectionModel().selectFirst();
    }

}
