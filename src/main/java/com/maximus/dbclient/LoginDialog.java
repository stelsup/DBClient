package com.maximus.dbclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.Collections;


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
    private PasswordField txtPass;

    public ButtonType btnResult = ButtonType.CANCEL;
    public String[] strCredentials  = new String[2];
    private String userName;

    public void onShow() {
        txtName.setPromptText("Имя пользователя");
        txtPass.setPromptText("Пароль");
    }

    @FXML
    private void btnOKClick(ActionEvent event) {

        strCredentials[0] = txtName.getText();
        strCredentials[1] = txtPass.getText();


        if (checkCredentials()) {
            btnResult = ButtonType.OK;
            Controller.getInstance().setUserName(userName);
            this.closeWindow();
        }else {
            ButtonType[] buttons = new ButtonType[2];
            buttons[0] = ButtonType.OK;
            buttons[1] = ButtonType.CANCEL;
            Utils.MessageBox(4, "Ошибка", "Пользователь или пароль введены неверно!",
                    Alert.AlertType.WARNING, buttons);
        }

    }
    @FXML
    private void btnCancelCLick(ActionEvent event) {
        btnResult = ButtonType.CANCEL;
        this.closeWindow();
    }

    public ButtonType getBtnResult() {
        return this.btnResult;
    }


    private boolean checkCredentials() {

        if (!checkName(Controller.getInstance().getPayersList())){
            return false;
        }
        return checkPass(Controller.getInstance().getPassword(userName));

    }


    private boolean checkName(String[] payers) {

        String userName = Utils.trimSpaces(strCredentials[0]);
        String[] users = Arrays.copyOf(payers,payers.length);

        for(int i = 0; i < users.length; i++) {
            users[i] = Utils.trimSpaces(users[i]);
            if (users[i].equalsIgnoreCase(userName)){
                this.userName = payers[i];
                return true;
            }
        }

        return false;
    }

    private boolean checkPass(String password) {
        return strCredentials[1].equals(password);
    }



}
