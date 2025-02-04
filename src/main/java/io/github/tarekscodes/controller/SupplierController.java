package io.github.tarekscodes.controller;

import java.util.ArrayList;
import java.util.List;

import io.github.tarekscodes.db.DBConnector;
import io.github.tarekscodes.models.SupplierDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SupplierController {
    @FXML private TableView<SupplierDTO> supplierTable;
    @FXML private TableColumn<SupplierDTO, String> supplierNumberColumn;
    @FXML private TableColumn<SupplierDTO, String> supplierNameColumn;
    @FXML private TableColumn<SupplierDTO, String> supplierStatusColumn;
    @FXML private TableColumn<SupplierDTO, String> supplierPhoneColumn;
    @FXML private TableColumn<SupplierDTO, String> supplierEmailColumn;
    @FXML private TextField supplierNameField;
    @FXML private TextField supplierNumberField;
    @FXML private TextField supplierphoneNumberField;
    @FXML private TextField supplierEmailField;
    @FXML private Button supplierSearchButton;
    @FXML private Button supplierClearSearchFieldsButton;
    DBConnector dbconnector = DBConnector.getINSTANCE();


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
        
        // TODO: Nach dem letzten Button soll der Fokus wieder auf das erste Textfeld gesetzt werden
        if (event.getCode() == KeyCode.TAB) {            
            if (event.getSource() instanceof TextField currentField) {
                if (currentField.equals(supplierNumberField)) {
                    supplierphoneNumberField.requestFocus();
                } else if (currentField.equals(supplierphoneNumberField)) {
                    supplierNameField.requestFocus();
                } else if (currentField.equals(supplierNameField)) {
                    supplierEmailField.requestFocus();
                } else if (currentField.equals(supplierEmailField)) {
                    supplierSearchButton.requestFocus();
                }
            }
        }
        else if (event.getSource() instanceof Button currentButton) {
            if (currentButton.equals(supplierSearchButton)) {
                supplierClearSearchFieldsButton.requestFocus();
            } else if (currentButton.equals(supplierClearSearchFieldsButton)) {
                supplierNumberField.requestFocus();
            }
        }
    }

    @FXML
    public void clearSearchFields() {

        supplierNameField.setText(null);
        supplierNumberField.setText(null);
        supplierphoneNumberField.setText(null);
        supplierEmailField.setText(null);
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
        supplier.setFirstContactPhoneNumber(supplierphoneNumberField.getText());
        supplier.setFirstContactEmail(supplierEmailField.getText());

        generateSQLQuery(supplier);
    }

    // TODO: Methode erstellt aus den Suchparametern eine dynamische SQL-Query und leitet diese an DBConnector weiter
    // Comoboxen und Choicebox zur Query hinzufügen
    private void generateSQLQuery(SupplierDTO supplier) {
    

        String supplierNameTrimmed = supplier.getSupplierName().trim();
        String supplierNumberTrimmed = supplier.getSupplierNumber().trim();
        String supplierFirstContactPhoneNumberTrimmed = supplier.getFirstContactPhoneNumber().trim();
        String supplierFirstContactEmailTrimmed = supplier.getFirstContactEmail().trim();

        StringBuilder query = new StringBuilder();

        String getSupplierString =  "SELECT supplier.supplierNumber, supplier.supplierName, supplier.supplierStatus, contactPerson.phonePrefix, contactPerson.phoneNumber, contactPerson.email " +
                                    "FROM supplier " +
                                    "LEFT JOIN supplier_contactPerson ON supplier.supplierID = supplier_contactPerson.supplierID " +
                                    "LEFT JOIN contactPerson ON contactPerson.contactPersonID = supplier_contactPerson.contactPersonID";

        /*
         * TODO:
         * Abfragen der Suchfelder
         * query dynamisch erstellen
         * prüfen ob verschieben in DBConnector sinnvoll
         * redundanter Code - getSupplierString String - bereits in DBConnector enthalten
         * Methode zum befüllen der Columns, um redundanten Code zu vermeiden
        */
        
        query.append(getSupplierString + " WHERE 1=1");
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
    
        List<SupplierDTO> supplier  = (ArrayList<SupplierDTO>) dbconnector.getAllSupplier();

        ObservableList<SupplierDTO> observableSupplierList = FXCollections.observableArrayList(supplier);

        /*
         * TODO:
         * Eigene Methode zum befüllen der Columns nutzen
         */

        supplierNumberColumn.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactPhoneNumber"));
        supplierEmailColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactEmail"));
        supplierStatusColumn.setCellValueFactory(new PropertyValueFactory<>("supplierStatus"));
        
    
        supplierTable.setItems(observableSupplierList);
    }
}
