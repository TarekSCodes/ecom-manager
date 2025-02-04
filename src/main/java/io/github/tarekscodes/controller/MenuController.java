package io.github.tarekscodes.controller;

import java.io.IOException;

import io.github.tarekscodes.App;
import javafx.fxml.FXML;

public class MenuController {
    @FXML
    public void switchToHome() throws IOException {
        App.setCenter("home");
    }

    @FXML
    public void switchToArticle() throws IOException {
        App.setCenter("article");
    }

    @FXML
    public void switchToSupplier() throws IOException {
        App.setCenter("supplierSearchView");
    }

    @FXML
    public void switchToCustomer() throws IOException {
        App.setCenter("customer");
    }
}


