package main.java.librinno.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.librinno.model.Database;
import main.java.librinno.model.Main;

import java.util.Scanner;

/**
 *
 */
public class MainApp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        String login, pass;
        Scanner sc = new Scanner(System.in);
        System.out.println("Write your login in MySQL:");
        Main.setUSER(sc.nextLine());
        System.out.println("Write your password in MySQL:");
        Main.setPASS(sc.nextLine());
        Database db=new Database();
        db.creationLocalDB(Main.getUSER(),Main.getPASS());




        launch(args);
    }

}
