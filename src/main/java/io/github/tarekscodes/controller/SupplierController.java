package io.github.tarekscodes.controller;

import io.github.tarekscodes.models.SupplierDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
    @FXML private ComboBox<String> cityField;
    @FXML private ComboBox<String> countryField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private ChoiceBox<String> statusField;
    @FXML private ComboBox<String> categoryField;


    @FXML
    private void initialize() {
        
    }

    /**
     * Liest die Werte aus den Textfeldern aus, erstellt ein SupplierDTO-Objekt.
     *
     * @param event Das ActionEvent, das ausgelöst wurde, als die Aktion ausgeführt wurde.
     */
    @FXML
    private void readTextFields(ActionEvent event) {
        
        SupplierDTO supplier = new SupplierDTO();
        supplier.setSupplierName(supplierNameField.getText());
        supplier.setSupplierPLZ(plzField.getText());
        supplier.setSupplierNumber(supplierNumberField.getText());
        supplier.setSuppliercity(cityField.getValue());
        supplier.setSupplierCountry(countryField.getValue());
        supplier.setSupplierPhoneNumber(phoneNumberField.getText());
        supplier.setSupplierEmail(emailField.getText());
        supplier.setSupplierStatus(statusField.getValue());
        supplier.setSupplierCategory(categoryField.getValue());

        generateSQLQuery(supplier);
    }

    // TODO: Methode erstellt aus den Suchparametern eine dynamische SQL-Query und leitet diese an DBConnector weiter
    private void generateSQLQuery(SupplierDTO supplier) {
    
        
    }

    // TODO:
    // 2. Implementierung der Methode zum Laden der Supplier-Daten in die Tabelle

}