package io.github.tarekscodes.controller;

import java.util.HashMap;
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

public class SupplierSearchController {
    
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
    ObservableList<SupplierDTO> observableSupplierList = FXCollections.observableArrayList();


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
        supplier.setSupplierName(supplierNameField.getText().trim());
        supplier.setSupplierNumber(supplierNumberField.getText().trim());
        supplier.setFirstContactPhoneNumber(supplierphoneNumberField.getText().trim());
        supplier.setFirstContactEmail(supplierEmailField.getText().trim());

        searchSuppliers(supplier);
    }
    

    private void searchSuppliers(SupplierDTO supplier) {

        // Check if all fields are empty
        if (supplier.getSupplierName().isEmpty() && 
            supplier.getSupplierNumber().isEmpty() && 
            supplier.getFirstContactPhoneNumber().isEmpty() && 
            supplier.getFirstContactEmail().isEmpty()) {
            return;
        }

        // Using HashMap<String, String> to store search properties
        HashMap<String, String> searchProperties = new HashMap<>();

        if (!supplier.getSupplierName().isEmpty()) {
            searchProperties.put("supplierName", supplier.getSupplierName());
        }
        if (!supplier.getSupplierNumber().isEmpty()) {
            searchProperties.put("supplierNumber", supplier.getSupplierNumber());
        }
        if (!supplier.getFirstContactPhoneNumber().isEmpty()) {
            searchProperties.put("supplierPhoneNumber", supplier.getFirstContactPhoneNumber());
        }
        if (!supplier.getFirstContactEmail().isEmpty()) {
            searchProperties.put("supplierEmail", supplier.getFirstContactEmail());
        }

        List<SupplierDTO> suppliers = dbconnector.findSuppliers(searchProperties);

        // Clear the observable list before adding new search results
        observableSupplierList.clear();
        observableSupplierList.setAll(suppliers);

        setupSupplierTableColumns();

        supplierTable.setItems(observableSupplierList);
    }

    /**
     * Lädt alle Lieferanten aus der Datenbank und bindet die Spalten der TableView
     * an die entsprechenden Properties des SupplierDTO-Objekts.
     * Die Daten werden über den DBConnector abgerufen und in einer ObservableList gespeichert.
     */
    private void initializeSuppliersTable() {
    
        List<SupplierDTO> suppliers  = dbconnector.getAllSuppliers();
        
        observableSupplierList.setAll(suppliers);

        setupSupplierTableColumns();
        
        supplierTable.setItems(observableSupplierList);
    }

    private void setupSupplierTableColumns() {

        supplierNumberColumn.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactPhoneNumber"));
        supplierEmailColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactEmail"));
        supplierStatusColumn.setCellValueFactory(new PropertyValueFactory<>("supplierStatus"));
    }
}
