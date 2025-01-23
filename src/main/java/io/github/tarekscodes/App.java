package io.github.tarekscodes;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static BorderPane mainLayout;

    @Override
    public void start(Stage stage) throws IOException {
        mainLayout = new BorderPane();
        scene = new Scene(mainLayout, 1000, 600);
        scene.getStylesheets().add(App.class.getResource("stylesheets/styles.css").toExternalForm());
        
        VBox menu = FXMLLoader.load(App.class.getResource("mainMenu.fxml"));
        mainLayout.setLeft(menu);
        
        setCenter("home");
        
        stage.setScene(scene);
        stage.show();
    }

    public static void setCenter(String fxml) throws IOException {
        Parent content = loadFXML(fxml);
        mainLayout.setCenter(content);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}