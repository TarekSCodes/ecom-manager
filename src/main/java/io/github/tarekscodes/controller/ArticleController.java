package io.github.tarekscodes.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import io.github.tarekscodes.App;

public class ArticleController {

    @FXML
    private void switchToHome() throws IOException {
        App.setCenter("home");
    }
}