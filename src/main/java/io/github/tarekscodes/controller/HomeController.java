package io.github.tarekscodes.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import io.github.tarekscodes.App;

public class HomeController {

    @FXML
    protected void switchToArticles() throws IOException {
        App.setCenter("secondary");
    }

    @FXML
    protected void switchToHome() throws IOException {
        App.setCenter("home");
    }
}
