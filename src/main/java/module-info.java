module com.maximus.dbclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    //requires org.controlsfx.controls;
    //requires com.dlsc.formsfx;
    //requires org.kordamp.ikonli.javafx;
    //requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
    requires java.sql;
    //requires com.gluonhq.charm.glisten;



    opens com.maximus.dbclient to javafx.fxml;
    exports com.maximus.dbclient;
    exports com.maximus.dbclient.DB;
    opens com.maximus.dbclient.DB to javafx.fxml;
    exports com.maximus.dbclient.GUICore;
    opens com.maximus.dbclient.GUICore to javafx.fxml;
    exports com.maximus.dbclient.GUIControllers;
    opens com.maximus.dbclient.GUIControllers to javafx.fxml;
    exports com.maximus.dbclient.ItemInfo;
    opens com.maximus.dbclient.ItemInfo to javafx.fxml;
}