package View;

import Model.Client;
import Model.Store;
import Model.StoreQueue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("queue.fxml")));
        primaryStage.setTitle("Home");
        primaryStage.setScene(new Scene(root, 1037, 511));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
