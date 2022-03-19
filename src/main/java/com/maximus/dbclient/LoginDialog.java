package com.maximus.dbclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class LoginDialog extends GUIController{
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    //private com.gluonhq.charm.glisten.control.TextField txtName;
    private TextField txtName;
    @FXML
    //private com.gluonhq.charm.glisten.control.TextField txtPass;
    private TextField txtPass;

    public ButtonType btnResult = ButtonType.CANCEL;
    public String[] strCredentials  = new String[2];

    public void onShow() {
        // todo: че-то там
    }

    @FXML
    private void btnOKClick(ActionEvent event) {

        String userName = txtName.getText();

        // get all users list
        //String[] users = Controller.getInstance().getPayersList();

        // todo: check user in list...
        //...

//        if(usera netu)
//        {
//            ButtonType[] buttons = new ButtonType[2];
//            buttons[0] = ButtonType.OK;
//            buttons[1] = ButtonType.CANCEL;
//            Utils.MessageBox(4, "Ошибка", "Такого пользователя не существует!",
//                    Alert.AlertType.WARNING, buttons);
//        }
        //else {

        strCredentials[0] = userName;
        strCredentials[1] = txtPass.getText();

        btnResult = ButtonType.OK;

        this.closeWindow();
        //}
    }
    @FXML
    private void btnCancelCLick(ActionEvent event) {
        btnResult = ButtonType.CANCEL;
        this.closeWindow();
        //Platform.exit();
    }

    public ButtonType getBtnResult() {
        return this.btnResult;
    }
}
