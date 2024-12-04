module org.example.imageapp {
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
    requires javafx.swing;
    requires metadata.extractor;
    requires javaxt.core;

    opens org.example.imageapp to javafx.fxml;
    opens org.example.imageapp.controllers to javafx.base;
    exports org.example.imageapp;
}