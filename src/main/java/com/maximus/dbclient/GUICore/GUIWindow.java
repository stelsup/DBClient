package com.maximus.dbclient.GUICore;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


public class GUIWindow {
    private Stage stage;
    private FXMLLoader loader;

    public GUIWindow(Stage s, FXMLLoader l) {
        this.stage = s;
        this.loader = l;
    }

    public GUIController getController() {
        return loader.getController();
    }

    public Stage getStage() {
        return this.stage;
    }
}
