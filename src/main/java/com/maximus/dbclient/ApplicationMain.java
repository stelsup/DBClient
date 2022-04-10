package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBController;
import com.maximus.dbclient.DB.DBResult;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.maximus.dbclient.Utils.showWindow;


public class ApplicationMain extends javafx.application.Application {
    DBController dbController;
    Controller controller;

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage stage) throws IOException {
        dbController = DBController.getInstance();
        controller = Controller.getInstance();


        GUIWindow wndLoginDialog = showWindow("LoginDialog.fxml",
                    new GUIParam(Modality.NONE, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),280,350,"Авторизация пользователя");

        LoginDialog loginDialog = (LoginDialog)wndLoginDialog.getController();
        if(loginDialog.getBtnResult() == ButtonType.CANCEL)
            return;

        GUIWindow wndMain = showWindow("MainWindow.fxml",
                    new GUIParam(Modality.NONE, null, GUIParam.ShowType.SHOWTYPE_SHOWNORMAL),750,1062,"Система платежей [ "
                        + Controller.getInstance().getUserName() + " ]");

        MainWindow mainWindow = (MainWindow)wndMain.getController();



    }

    @Override
    public void stop() throws Exception {
        dbController.disconnect();


    }

    public static void main(String[] args) {

       launch();
    }

}
