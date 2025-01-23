package io.github.tarekscodes.controller;

import java.io.IOException;

import io.github.tarekscodes.App;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    private void switchToHome() throws IOException {
        App.setCenter("home");
    }

    @FXML
    private void switchToArticle() throws IOException {
        App.setCenter("article");
    }

    @FXML
    private void switchToSupplier() throws IOException {
        App.setCenter("supplier");
    }

    @FXML
    private void switchToCustomer() throws IOException {
        App.setCenter("customer");
    }
}


