module io.github.tarekscodes {
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires transitive javafx.graphics;

    opens io.github.tarekscodes to javafx.fxml;
    opens io.github.tarekscodes.controller to javafx.fxml;
    exports io.github.tarekscodes;
}
