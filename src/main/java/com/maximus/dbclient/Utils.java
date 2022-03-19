package com.maximus.dbclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Utils {

    public static String getEtcPath() {
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return path + "../../etc/";
    }

    public static String clearDBData(String input) {
        String out = input.trim();
        return out;
    }

    public static GUIWindow showWindow(String fxmlName, GUIParam guiParam) throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(fxmlName));
        Scene scene = new Scene(fxmlLoader.load()/*, 320, 240*/);
        //stage.setTitle("Start");
        stage.initModality(guiParam.modality);
        stage.initOwner(guiParam.ownerParent);

//        if(guiParam.modality == Modality.APPLICATION_MODAL)
//            stage.hide();

        stage.setScene(scene);

        GUIController c = fxmlLoader.getController();
        c.setScene(scene);

        c.onShow();

        if(guiParam.showType == GUIParam.ShowType.SHOWTYPE_SHOWNORMAL)
            stage.show();
        else if(guiParam.showType == GUIParam.ShowType.SHOWTYPE_SHOWWAIT)
            stage.showAndWait();

       return new GUIWindow(stage, fxmlLoader);
    }

    public static ButtonType MessageBox(int hndl, String title, String text, Alert.AlertType type, ButtonType[] buttons) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        //alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText(text);
//        alert.showAndWait().ifPresent(rs -> {
//            if (rs == ButtonType.OK) {
//                System.out.println("Pressed OK.");
//            }
//        });
        alert.showAndWait();
        return alert.getResult();
    }
}

