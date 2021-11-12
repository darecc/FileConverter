package sample;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Poprawiacz napisów do polskich filmów w formacie SRT");
        primaryStage.setScene(new Scene(root, 370, 275));
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(350);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}