module com.testeexemplo.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.financepro.model to javafx.fxml;
    exports com.financepro.model;

    exports com.financepro.model.frontend;
    opens com.financepro.model.frontend to javafx.fxml;

    exports com.financepro.model.frontend.controllers;
    opens com.financepro.model.frontend.controllers to javafx.fxml;

    exports com.financepro.model.backend;
    opens com.financepro.model.backend to javafx.fxml;

    exports com.financepro.model.frontend.launcher;
    opens com.financepro.model.frontend.launcher to javafx.fxml;
}