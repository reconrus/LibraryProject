package main.java.librinno.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.librinno.model.Database;
import main.java.librinno.model.Main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public static void main(String[] args) throws IOException {
        Path filePath_1= Paths.get("dbinf.txt");
        if (!Files.exists(filePath_1)) {
            String login, pass;
            FileWriter wr = new FileWriter("dbinf.txt");
            Scanner sc = new Scanner(System.in);
            System.out.println("Write your login in MySQL:");
            String temp = sc.nextLine();
            wr.write(temp);
            wr.write("\n");
            Main.setUSER(temp);
            System.out.println("Write your password in MySQL:");
            temp = sc.nextLine();
            wr.write(temp);
            wr.close();
            Main.setPASS(temp);
        }
        else{
            Scanner sc = new Scanner(new File("dbinf.txt"));
            Main.setUSER(sc.nextLine());
            Main.setPASS(sc.nextLine());
            sc.close();
        }
        Database db = new Database();
        db.creationLocalDB(Main.getUSER(), Main.getPASS());
        launch(args);

    }

}
