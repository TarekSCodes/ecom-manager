package io.github.tarekscodes.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import io.github.tarekscodes.App;

public class MenuController {
    @FXML
    private void switchToHome() throws IOException {
        App.setCenter("home");
    }

    @FXML
    private void switchToArticles() throws IOException {
        App.setCenter("articles");
    }
}

