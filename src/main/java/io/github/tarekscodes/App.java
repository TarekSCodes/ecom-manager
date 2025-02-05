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
        scene = new Scene(mainLayout);
        scene.getStylesheets().add(App.class.getResource("stylesheets/styles.css").toExternalForm());
        
        VBox menu = FXMLLoader.load(App.class.getResource("mainMenu.fxml"));
        mainLayout.setLeft(menu);
        setCenter("home");
        setBottom("bottomBar");

        stage.setMinWidth(1000);
        stage.setMinHeight(600);

        stage.setMaxWidth(1920);
        stage.setMaxHeight(1080);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the center of the main layout with the content loaded from the specified FXML file.
     *
     * @param fxml the path to the FXML file to load
     * @throws IOException if an I/O error occurs during loading of the FXML file
     */
    public static void setCenter(String fxml) throws IOException {
        Parent content = loadFXML(fxml);
        mainLayout.setCenter(content);
    }

    public static void setBottom(String fxml) throws IOException {
        Parent content = loadFXML(fxml);
        mainLayout.setBottom(content);
    }

    /**
     * Loads an FXML file and returns the corresponding Parent object.
     *
     * @param fxml the name of the FXML file to load, without the ".fxml" extension
     * @return the loaded Parent object
     * @throws IOException if an I/O error occurs during loading
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}