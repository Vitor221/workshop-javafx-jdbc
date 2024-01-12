module com.eduardo.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.eduardo.workshopjavafxjdbc to javafx.graphics, javafx.fxml;

    opens com.eduardo.workshopjavafxjdbc.gui to javafx.graphics, javafx.fxml;

    opens com.eduardo.workshopjavafxjdbc.model.entities to javafx.graphics, javafx.fxml, javafx.base;

    opens com.eduardo.workshopjavafxjdbc.model.services to javafx.graphics, javafx.fxml;

    exports com.eduardo.workshopjavafxjdbc;
}