package io.github.tarekscodes.db;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.github.tarekscodes.models.SupplierDTO;

public class DBConnector {

    private static DBConnector INSTANCE;

    // TODO:
    // 1. Datenbank import, export mittles .json
    // 2. Implementierung eines Connection-Pools

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
     * Gibt den absoluten Pfad zur SQLite-Datenbank als JDBC-URL zurück.
     * Der Pfad wird relativ zum Projektverzeichnis konstruiert.
     * 
     * @return JDBC-URL als String im Format "jdbc:sqlite:/absoluter/pfad/zur/datenbank.db"
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
     * Ruft alle Lieferanten aus der Datenbank ab und gibt sie als Liste zurück.
     * Die Methode führt eine SQL-Abfrage aus, die den Namen und die Nummer jedes Lieferanten
     * aus der 'supplier' Tabelle abruft.
     * 
     * @return Eine Liste von SupplierDTO-Objekten, die alle Lieferanten repräsentiert.
     *         Falls keine Lieferanten gefunden werden oder ein Datenbankfehler
     *         auftritt, wird eine leere Liste zurückgegeben.
     * @see SupplierDTO
     */
    public List<SupplierDTO> getAllSuppliers() {
        String getAllSupplierString =   "SELECT supplier.supplierNumber, supplier.supplierName, supplier.supplierStatus, contactPerson.phonePrefix, contactPerson.phoneNumber, contactPerson.email " +
                                        "FROM supplier " +
                                        "LEFT JOIN supplier_contactPerson ON supplier.supplierID = supplier_contactPerson.supplierID " +
                                        "LEFT JOIN contactPerson ON contactPerson.contactPersonID = supplier_contactPerson.contactPersonID;";
        
        List<SupplierDTO> suppliersList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDBPath());
             PreparedStatement pstmt = conn.prepareStatement(getAllSupplierString);
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
