package io.github.tarekscodes.controller;

import java.util.ArrayList;
import java.util.List;

import io.github.tarekscodes.db.DBConnector;
import io.github.tarekscodes.models.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    @FXML private TextField supplierNumberField;
    @FXML private TextField supplierphoneNumberField;
    @FXML private TextField supplierEmailField;


    @FXML
    public void initialize() {

        initializeSuppliersTable();
    }

    /**
     * Diese Methode handelt den Tab-Order für die Textfelder.
     * @param event Das KeyEvent, das ausgelöst wurde.
     */
    @FXML
    public void handleTabOrder(KeyEvent event) {
        TextField currentField = (TextField) event.getSource();

        if (event.getCode() == KeyCode.TAB) {
            if (currentField.equals(supplierNameField)) {
                supplierphoneNumberField.requestFocus();
            } else if (currentField.equals(supplierphoneNumberField)) {
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
        supplier.setSupplierNumber(supplierNumberField.getText());
        //supplier.setSupplierPhoneNumber(supplierphoneNumberField.getText());
        //supplier.setSupplierEmail(supplierEmailField.getText());

        generateSQLQuery(supplier);
    }

    // TODO: Methode erstellt aus den Suchparametern eine dynamische SQL-Query und leitet diese an DBConnector weiter
    // Comoboxen und Choicebox zur Query hinzufügen
    private void generateSQLQuery(SupplierDTO supplier) {
    

        String supplierNameTrimmed = supplier.getSupplierName().trim();
        String supplierNumberTrimmed = supplier.getSupplierNumber().trim();

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM supplier WHERE 1=1");
        if (!supplierNameTrimmed.isEmpty()) {
            query.append(" AND supplierName = '" + supplierNameTrimmed + "'");
        }
        // if (!supplier.getSupplierPhoneNumber().trim().isEmpty()) {
        //     query.append(" AND supplierPhoneNumber" + " Like " + "'%" + supplier.getSupplierPhoneNumber().trim() + "%'");
        // }
        if (!supplierNumberTrimmed.isEmpty()) {
            query.append(" AND supplierNumber" + " Like " + "'%" + supplierNumberTrimmed + "%'");
        }
        // if (!supplier.getSupplierEmail().trim().isEmpty()) {
        //     query.append(" AND supplierEmail" + " Like " + "'%" + supplier.getSupplierEmail().trim() + "%'");
        // }
        
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
