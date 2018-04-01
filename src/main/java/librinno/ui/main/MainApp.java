package main.java.librinno.ui.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.librinno.model.Database;
import main.java.librinno.model.Main;
import java.io.File;
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
        Path filePath_1 = Paths.get("dbinf.txt");
        if (!Files.exists(filePath_1)) {
            Parent parent = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/main/dbinfo.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        }
        Scanner sc = new Scanner(new File("dbinf.txt"));
        Main.setDbUrlwodbk(sc.nextLine());
        Main.setUSER(sc.nextLine());
        Main.setPASS(sc.nextLine());
        sc.close();
        Database db = new Database();
        db.creationLocalDB(Main.getUSER(), Main.getPASS());


        Parent root = FXMLLoader.load(getClass().getResource("/main/java/librinno/ui/login/LoginScreen.fxml"));

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
