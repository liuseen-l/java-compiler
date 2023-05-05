package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("compiler.fxml"));
        primaryStage.setTitle("H Compiler");
        primaryStage.setScene(new Scene(root, 1500 , 830));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

