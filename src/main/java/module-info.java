module com.eduardo.workshopjavafxjdbc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.eduardo.workshopjavafxjdbc to javafx.fxml;
    exports com.eduardo.workshopjavafxjdbc;
}