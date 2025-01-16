module io.github.tarekscodes {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires transitive javafx.graphics;
    requires MaterialFX;

    opens io.github.tarekscodes to javafx.fxml;
    exports io.github.tarekscodes;
}
