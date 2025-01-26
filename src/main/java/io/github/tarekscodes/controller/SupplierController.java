package io.github.tarekscodes.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SupplierController {
    @FXML private TableView<?> supplierTable;
    @FXML private TableColumn<?, ?> idColumn;
    @FXML private TableColumn<?, ?> nameColumn;
    @FXML private TableColumn<?, ?> addressColumn;
    @FXML private TableColumn<?, ?> phoneColumn;
    @FXML private TableColumn<?, ?> emailColumn;
    @FXML private TextField supplierNameField;
    @FXML private TextField plzField;
    @FXML private TextField supplierNumberField;
    @FXML private TextField cityField;
    @FXML private TextField countryField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private TextField statusField;
    @FXML private TextField categoryField;


    @FXML
    private void initialize() {
        
    }

    // Todo: 
    // 1. Implementierung einer gemeinsamen Methode für alle Textfelder der Supplier-View
    //  - Methode soll die Textfelder auslesen und in einem Objekt speichern - DTO
    //  - Methode erstellt aus den Suchparametern eine dynamische SQL-Query und leitet diese an DBConnector weiter
    // 2. Implementierung der Methode zum Laden der Supplier-Daten in die Tabelle

}