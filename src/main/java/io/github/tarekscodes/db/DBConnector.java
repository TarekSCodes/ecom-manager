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

    // TODO:
    // 1. Datenbank import, export mittles .json
    // 2. Implementierung eines Connection-Pools


    /**
     * Gibt den absoluten Pfad zur SQLite-Datenbank als JDBC-URL zurück.
     * Der Pfad wird relativ zum Projektverzeichnis konstruiert.
     * 
     * @return JDBC-URL als String im Format "jdbc:sqlite:/absoluter/pfad/zur/datenbank.db"
     */
    private static final String getDBPath() {
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

    public static List<SupplierDTO> getAllSupplier() {
        String getAllSupplierString = "SELECT supplierName, supplierNumber FROM supplier";
        List<SupplierDTO> supplierList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(getDBPath());
             PreparedStatement pstmt = conn.prepareStatement(getAllSupplierString);
             ResultSet rs = pstmt.executeQuery(); 
            ) {

            while (rs.next()) {
                
                /*
                 * TODO: Daten jeder Zeile auslesen, in einer List (HashMap?) speichern
                 * und return
                 */

                SupplierDTO supplier = new SupplierDTO();
                supplier.setSupplierName(rs.getString("supplierName"));
                supplier.setSupplierNumber(rs.getString("supplierNumber"));
                supplierList.add(supplier);
            }

            
        } catch (SQLException e) {
            System.err.println("Fehler beim Abfragen der Lieferanten: " + e.getMessage());
        }
        
        return supplierList;
    }
}
