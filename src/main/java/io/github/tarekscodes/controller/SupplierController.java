package io.github.tarekscodes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SupplierController {
    @FXML
    private TableView<?> supplierTable;
    
    @FXML
    private TableColumn<?, ?> idColumn;
    
    @FXML
    private TableColumn<?, ?> nameColumn;
    
    @FXML
    private TableColumn<?, ?> addressColumn;
    
    @FXML
    private TableColumn<?, ?> phoneColumn;
    
    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private void initialize() {
        // Hier kommt später die Initialisierungslogik
    }
} 