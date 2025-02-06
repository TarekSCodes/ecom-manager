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
            "SELECT DISTINCT supplier.supplierNumber, supplier.supplierName, supplier.supplierStatus, contactPerson.phonePrefix, contactPerson.phoneNumber, contactPerson.email " +
            "FROM supplier " +
            "LEFT JOIN supplier_contactPerson ON supplier.supplierID = supplier_contactPerson.supplierID " +
            "LEFT JOIN contactPerson ON contactPerson.contactPersonID = supplier_contactPerson.contactPersonID";

    // TODO:
    // 1. Import and export database using .json
    // 2. Implement a connection pool
    // 3. Retrieve the SupplierDTO ID from the database to use it when selecting a supplier in the table to fetch all information

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

    /**
     * Retrieves a list of all suppliers from the database.
     *
     * @return a list of SupplierDTO objects representing all suppliers.
     */
    public List<SupplierDTO> getAllSuppliers() {
        
        List<String> emptyList = new ArrayList<>();
        return createSupplierDTOFromResultSet(BASE_SUPPLIER_QUERY, emptyList);
    }

    /**
     * Finds suppliers based on the given search properties.
     *
     * @param searchProperties a HashMap containing search criteria where the key is the property name and the value is the property value.
     *                         Supported keys are:
     *                         - "supplierName": to search by supplier name (partial match).
     *                         - "supplierNumber": to search by supplier number (partial match).
     *                         - "supplierPhoneNumber": to search by supplier phone number (partial match).
     *                         - "supplierEmail": to search by supplier email (partial match).
     * @return a List of SupplierDTO objects that match the search criteria.
     */
    public List<SupplierDTO> findSuppliers(HashMap<String, String> searchProperties) {

        StringBuilder queryBuilder = new StringBuilder(BASE_SUPPLIER_QUERY);
        List<String> whereClauses = new ArrayList<>();
        List<String> searchValues = new ArrayList<>();


        if (searchProperties.containsKey("supplierName")) {
            whereClauses.add("supplier.supplierName LIKE ?");
            searchValues.add("%" + searchProperties.get("supplierName") + "%");
        }
        if (searchProperties.containsKey("supplierNumber")) {
            whereClauses.add("supplier.supplierNumber LIKE ?");
            searchValues.add("%" + searchProperties.get("supplierNumber") + "%");
        }
        if (searchProperties.containsKey("supplierPhoneNumber")) {
            whereClauses.add("contactPerson.phoneNumber LIKE ?");
            searchValues.add("%" + searchProperties.get("supplierPhoneNumber") + "%");
        }
        if (searchProperties.containsKey("supplierEmail")) {
            whereClauses.add("contactPerson.email LIKE ?");
            searchValues.add("%" + searchProperties.get("supplierEmail") + "%");
        }

        if (!whereClauses.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", whereClauses));
        }
        return createSupplierDTOFromResultSet(queryBuilder.toString(), searchValues);
    }

    /**
     * Creates a list of SupplierDTO objects from the result set of a SQL query.
     *
     * @param sqlQuery The SQL query to be executed.
     * @param searchValues The list of values to be set in the prepared statement.
     * @return A list of SupplierDTO objects created from the result set.
     * @throws SQLException If a database access error occurs or the SQL query is invalid.
     */
    private List<SupplierDTO> createSupplierDTOFromResultSet(String sqlQuery, List<String> searchValues) {
    
        List<SupplierDTO> suppliersList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDBPath());
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            ) {

            for (int i = 0; i < searchValues.size(); i++) {
                pstmt.setString(i + 1, searchValues.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
            
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
            }

        } catch (SQLException e) {
            System.err.println("Fehler beim Abfragen der Lieferanten: " + e.getMessage());
        }
        return suppliersList;
    }
}
