module com.example.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.inventorysystem.controller to javafx.fxml;
    opens com.example.inventorysystem.model to javafx.base;
    exports com.example.inventorysystem;
}