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
import javafx.scene.control.Tooltip;
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
     * This method handles the tab order for the text fields.
     * @param event The KeyEvent that was triggered.
     */
    @FXML
    public void handleTabOrder(KeyEvent event) {
        
        // After the last button, the focus should be set back to the first text field
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

        supplierNameField.setText("");
        supplierNumberField.setText("");
        supplierphoneNumberField.setText("");
        supplierEmailField.setText("");
        updateSupplierTable(dbconnector.getAllSuppliers());
    }

    /**
     * Reads the values from the text fields and creates a SupplierDTO object.
     *
     * @param event The ActionEvent that was triggered when the action was performed.
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
    

    /**
     * Searches for suppliers based on the provided SupplierDTO properties.
     * If all fields in the SupplierDTO are empty, the method returns without performing a search.
     * Otherwise, it constructs a search query using the non-empty fields and retrieves matching suppliers.
     * The search results are then used to update the observable supplier list and the supplier table.
     *
     * @param supplier the SupplierDTO containing the search criteria
     */
    private void searchSuppliers(SupplierDTO supplier) {

        // Check if all fields are empty
        if (supplier.getSupplierName().isEmpty() && 
            supplier.getSupplierNumber().isEmpty() && 
            supplier.getFirstContactPhoneNumber().isEmpty() && 
            supplier.getFirstContactEmail().isEmpty()) {
                List<SupplierDTO> allSupplier = dbconnector.getAllSuppliers();
                updateSupplierTable(allSupplier);
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

        updateSupplierTable(suppliers);
    }

    /**
     * Initializes the suppliers table by fetching all suppliers from the database,
     * setting up the table columns, and populating the table with the supplier data.
     */
    private void initializeSuppliersTable() {
    
        // initlize the table with all suppliers
        List<SupplierDTO> suppliers  = dbconnector.getAllSuppliers();
        updateSupplierTable(suppliers);

        Tooltip clearFieldsTooltip = new Tooltip("Suchfelder löschen");
        supplierClearSearchFieldsButton.setTooltip(clearFieldsTooltip);
    }

    private void updateSupplierTable(List<SupplierDTO> suppliers) {

        observableSupplierList.clear();
        observableSupplierList.setAll(suppliers);

        setupSupplierTableColumns();
        
        supplierTable.setItems(observableSupplierList);
    }

    /**
     * Configures the columns of the supplier table with the appropriate property values.
     */
    private void setupSupplierTableColumns() {

        supplierNumberColumn.setCellValueFactory(new PropertyValueFactory<>("supplierNumber"));
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactPhoneNumber"));
        supplierEmailColumn.setCellValueFactory(new PropertyValueFactory<>("firstContactEmail"));
        supplierStatusColumn.setCellValueFactory(new PropertyValueFactory<>("supplierStatus"));
    }
}
