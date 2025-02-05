package io.github.tarekscodes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerController {
    @FXML
    private TableView<?> customerTable;
    
    @FXML
    private TableColumn<?, ?> idColumn;
    
    @FXML
    private TableColumn<?, ?> firstNameColumn;
    
    @FXML
    private TableColumn<?, ?> lastNameColumn;
    
    @FXML
    private TableColumn<?, ?> addressColumn;
    
    @FXML
    private TableColumn<?, ?> phoneColumn;
    
    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private void initialize() {
        
    }
} 