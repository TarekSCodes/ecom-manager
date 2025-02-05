package io.github.tarekscodes.db;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.tarekscodes.models.SupplierDTO;

public class DBConnector {

    private static DBConnector INSTANCE;
    private static final String BASE_SUPPLIER_QUERY =
            "SELECT supplier.supplierNumber, supplier.supplierName, supplier.supplierStatus, contactPerson.phonePrefix, contactPerson.phoneNumber, contactPerson.email " +
            "FROM supplier " +
            "LEFT JOIN supplier_contactPerson ON supplier.supplierID = supplier_contactPerson.supplierID " +
            "LEFT JOIN contactPerson ON contactPerson.contactPersonID = supplier_contactPerson.contactPersonID";

    // TODO:
    // 1. Import and export database using .json
    // 2. Implement a connection pool

    private DBConnector() {
        // Privat Construktor, to prevent instantiation
    }

    public static DBConnector getINSTANCE() {
        // Singleton-Instance
        if (INSTANCE == null) {
            INSTANCE = new DBConnector();
        }
        return INSTANCE;
    }

    /**
     * Returns the absolute path to the SQLite database as a JDBC URL.
     * The path is constructed relative to the project directory.
     * 
     * @return JDBC URL as a string in the format "jdbc:sqlite:/absolute/path/to/database.db"
     */
    public static String getDBPath() {
        Path dbPath = Path.of("src", "main", "java", "io", "github", "tarekscodes", "db", "ecom_manager.db").toAbsolutePath();
        return "jdbc:sqlite:" + dbPath.toString();
    }

    public static void connect() {
        try (Connection conn = DriverManager.getConnection(getDBPath())) {
            System.out.println("Verbindung zur Datenbank hergestellt");
        } catch (SQLException e) {
            System.out.println("Fehler beim Verbinden mit der Datenbank: " + e.getMessage());
        }
    }

    public static void executeQuery(String query) {
        try (Connection conn = DriverManager.getConnection(getDBPath())) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Fehler beim Ausführen der Query: " + e.getMessage());
        }
    }

    public List<SupplierDTO> getAllSuppliers() {
                
        return createSupplierDTOFromResultSet(BASE_SUPPLIER_QUERY);
    }

    public List<SupplierDTO> findSuppliers(HashMap<String, String> searchProperties) {

        String searchString =   BASE_SUPPLIER_QUERY +
                                " WHERE supplier.supplierName LIKE ? OR supplier.supplierNumber LIKE ?" + 
                                " contactPerson.phoneNumber, contactPerson.email";
        
        // TODO: Create the query string dynamically based on the search properties
        //       and give it to the new method createSupplierDTOFromResultSet().                     
        
        System.out.println(searchString);

        return null;
    }

    private List<SupplierDTO> createSupplierDTOFromResultSet(String sqlQuery) {
    
        List<SupplierDTO> suppliersList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDBPath());
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
             ResultSet rs = pstmt.executeQuery();
            ) {

            while (rs.next()) {

                SupplierDTO supplier = new SupplierDTO(
                    rs.getString("supplierName"),
                    rs.getString("supplierNumber"),
                    rs.getString("email"),
                    rs.getString("supplierStatus")
                );
                supplier.setFirstContactPhoneNumber(rs.getString("phonePrefix") + " " + rs.getString("phoneNumber"));

                // Prevent output of "null" if no data is entered
                if (supplier.getFirstContactPhoneNumber().contains("null")) {
                    supplier.setFirstContactPhoneNumber(supplier.getFirstContactPhoneNumber().replace("null", ""));
                }
                suppliersList.add(supplier);
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Abfragen der Lieferanten: " + e.getMessage());
        }
        
        return suppliersList;
    }
}
