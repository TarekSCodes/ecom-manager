package io.github.tarekscodes.controller;

import java.util.ArrayList;
import java.util.List;

import io.github.tarekscodes.db.DBConnector;
import io.github.tarekscodes.models.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SupplierController {
    @FXML private TableView<SupplierDTO> supplierTable;
    @FXML private TableColumn<SupplierDTO, String> supplierNumberColumn;
    @FXML private TableColumn<SupplierDTO, String> nameColumn;
    @FXML private TableColumn<?, ?> addressColumn;
    @FXML private TableColumn<?, ?> phoneColumn;
    @FXML private TableColumn<?, ?> emailColumn;
    @FXML private TextField supplierNameField;
    @FXML private TextField supplierplzField;
    @FXML private TextField supplierNumberField;
    @FXML private ComboBox<String> suppliercityField;
    @FXML private ComboBox<String> suppliercountryField;
    @FXML private TextField supplierphoneNumberField;
    @FXML private TextField supplierEmailField;
    @FXML private ChoiceBox<String> supplierStatusField;
    @FXML private ComboBox<String> supplierCategoryField;

    // TODO: ComboBoxen initialisieren

    @FXML
    public void initialize() {

        // Hier wird die ChoiceBox für den Status der Lieferanten initialisiert
        supplierStatusField.getItems().addAll("Beliebig", "Aktiv", "Inaktiv");
        supplierStatusField.setValue("Beliebig");
        initializeSuppliersTable();
    }

    /**
     * Diese Methode handelt den Tab-Order für die Textfelder.
     * @param event Das KeyEvent, das ausgelöst wurde.
     */
    @FXML
    public void handleTabOrder(KeyEvent event) {
        TextField currentField = (TextField) event.getSource();

        // TODO: ComboBoxen und Choiceboxen integrieren in die Tab-Order

        if (event.getCode() == KeyCode.TAB) {
            if (currentField.equals(supplierNameField)) {
                supplierphoneNumberField.requestFocus();
            } else if (currentField.equals(supplierphoneNumberField)) {
                supplierplzField.requestFocus();
            } else if (currentField.equals(supplierplzField)) {
                supplierNumberField.requestFocus();
            } else if (currentField.equals(supplierNumberField)) {
                supplierEmailField.requestFocus();
            } else if (currentField.equals(supplierEmailField)) {
                supplierNameField.requestFocus();
            }
        }
    }

    /**
     * Liest die Werte aus den Textfeldern aus und erstellt ein SupplierDTO-Objekt.
     *
     * @param event Das ActionEvent, das ausgelöst wurde, als die Aktion ausgeführt wurde.
     */
    @FXML
    public void readTextFields(ActionEvent event) {
        
        SupplierDTO supplier = new SupplierDTO();
        supplier.setSupplierName(supplierNameField.getText());
        supplier.setSupplierPostalCode(supplierplzField.getText());
        supplier.setSupplierNumber(supplierNumberField.getText());
        supplier.setSupplierCity(suppliercityField.getValue());
        supplier.setSupplierCountry(suppliercountryField.getValue());
        //supplier.setSupplierPhoneNumber(supplierphoneNumberField.getText());
        //supplier.setSupplierEmail(supplierEmailField.getText());
        supplier.setSupplierStatus(supplierStatusField.getValue());
        //supplier.setSupplierCategory(supplierCategoryField.getValue());

        generateSQLQuery(supplier);
    }

    // TODO: Methode erstellt aus den Suchparametern eine dynamische SQL-Query und leitet diese an DBConnector weiter
    // Comoboxen und Choicebox zur Query hinzufügen
    private void generateSQLQuery(SupplierDTO supplier) {
    
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM supplier WHERE 1=1");
        if (!supplier.getSupplierName().trim().isEmpty()) {
            query.append(" AND supplierName = '" + supplier.getSupplierName().trim() + "'");
        }
        // if (!supplier.getSupplierPhoneNumber().trim().isEmpty()) {
        //     query.append(" AND supplierPhoneNumber" + " Like " + "'%" + supplier.getSupplierPhoneNumber().trim() + "%'");
        // }
        if (!supplier.getSupplierPostalCode().trim().isEmpty()) {
            query.append(" AND supplierPLZ" + " Like " + "'%" + supplier.getSupplierPostalCode().trim() + "%'");
        }
        if (!supplier.getSupplierNumber().trim().isEmpty()) {
            query.append(" AND supplierNumber" + " Like " + "'%" + supplier.getSupplierNumber().trim() + "%'");
        }
        // if (!supplier.getSupplierEmail().trim().isEmpty()) {
        //     query.append(" AND supplierEmail" + " Like " + "'%" + supplier.getSupplierEmail().trim() + "%'");
        // }
        /* if (!supplier.getSuppliercity().trim().isEmpty()) {
            query.append(" AND suppliercity" + " Like " + "'%" + supplier.getSuppliercity().trim() + "%'");
        }
        if (!supplier.getSupplierCountry().trim().isEmpty()) {
            query.append(" AND supplierCountry" + " Like " + "'%" + supplier.getSupplierCountry().trim() + "%'");
        }
        if (!supplier.getSupplierStatus().trim().isEmpty()) {
            query.append(" AND supplierStatus" + " Like " + "'%" + supplier.getSupplierStatus().trim() + "%'");
        }
        if (!supplier.getSupplierCategory().trim().isEmpty()) {
            query.append(" AND supplierCategory" + " Like " + "'%" + supplier.getSupplierCategory().trim() + "%'");
        } */

        System.out.println(query.toString());
    }

    /**
     * Lädt alle Lieferanten aus der Datenbank und bindet die Spalten 'Name' und 'Lieferantennummer'
     * an die entsprechenden Properties des SupplierDTO-Objekts.
     * Die Daten werden über den DBConnector abgerufen und in einer ObservableList gespeichert.
     */
    private void initializeSuppliersTable() {
    
        List<SupplierDTO> supplier  = (ArrayList<SupplierDTO>) DBConnector.getAllSupplier();

        ObservableList<SupplierDTO> observableSupplierList = FXCollections.observableArrayList(supplier);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierNumberColumn.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
    
        supplierTable.setItems(observableSupplierList);
    }
}
