package io.github.tarekscodes.db;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
}
