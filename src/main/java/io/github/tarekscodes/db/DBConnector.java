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

import io.github.tarekscodes.models.ContactPersonDTO;
import io.github.tarekscodes.models.SupplierDTO;
import static io.github.tarekscodes.utils.DTOUtils.sanitizeString;

public class DBConnector {

    private static DBConnector INSTANCE;
    private static final String BASE_SUPPLIER_QUERY =
            "SELECT DISTINCT supplier.supplierID, supplier.supplierNumber, supplier.supplierName, " +
            "supplier.supplierStatus, contactPerson.phonePrefix, contactPerson.phoneNumber, contactPerson.email " +
            "FROM supplier " +
            "LEFT JOIN supplier_contactPerson ON supplier.supplierID = supplier_contactPerson.supplierID " +
            "LEFT JOIN contactPerson ON contactPerson.contactPersonID = supplier_contactPerson.contactPersonID " +
            "LEFT JOIN website ON website.supplierID = supplier.supplierID";
    private static final String BASE_CONTACTPERSON_QUERY =
            "SELECT DISTINCT * " +
            "FROM contactPerson cp " +
            "JOIN supplier_contactPerson scp ON cp.contactPersonID = scp.contactPersonID " +
            "JOIN supplier s ON scp.supplierID = s.supplierID" +
            "WHERE s.supplierID = ?";

    // TODO:
    // 1. Import and export database using .json
    // 2. Implement a connection pool

    private DBConnector() {
        // Privat Constructor, to prevent instantiation
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
     * Creates a list of SupplierDTO objects from the result set of a search-based SQL query.
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
                        rs.getInt("supplierID"),
                        rs.getString("supplierName"),
                        rs.getString("supplierNumber"),
                        rs.getString("email"),
                        rs.getString("supplierStatus")
                    );
                    supplier.setFirstContactPhoneNumber(rs.getString("phonePrefix") + " " + rs.getString("phoneNumber"));
                    

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

    /**
     * Creates a list of ContactPersonDTO objects from the result set.
     * 
     * @param sqlQuery The SQL query to be executed.
     * @return A list of ContactPersonDTO objects created from the result set.
     * @throws SQLException If a database access error occurs or the SQL query is invalid.
     */
    private List<ContactPersonDTO> createContactPersonDTOFromResultSet(String sqlQuery) {

        List<ContactPersonDTO> contactPersonList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDBPath());
            PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
            ) {

                try (ResultSet rs = pstmt.executeQuery()) {

                    while (rs.next()) {

                        ContactPersonDTO person = new ContactPersonDTO(
                            rs.getInt("contactPersonID"),
                            rs.getString("lastName")
                        );
                        person.setFirstName(sanitizeString(rs.getString("firstName")));
                        person.setEmail(sanitizeString(rs.getString("email")));
                        person.setPhonePrefix(sanitizeString(rs.getString("phonePrefix")));
                        person.setPhoneNumber(sanitizeString(rs.getString("phoneNumber")));
                        person.setFaxNumber(sanitizeString(rs.getString("faxNumber")));
                        
                        contactPersonList.add(person);
                    }
                }

            } catch (SQLException e) {
                System.err.println("Fehler beim Abfragen der Kontakt Personen: " + e.getMessage());
            }

        return contactPersonList;
    }
}
