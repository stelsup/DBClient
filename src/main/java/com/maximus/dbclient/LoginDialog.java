package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;

import java.util.Arrays;


public class LoginDialog extends GUIController{
    //debug
    public boolean DEBUG = true;
    //debug
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPass;

    public ButtonType btnResult = ButtonType.CANCEL;
    public String[] strCredentials  = new String[2];
    private String userName;

    public void onShow() {

       // this.scene.sta   stage.getIcons().add(new Image(getClass().getResource(getImagesPath() + "xxx").toExternalForm() ));

        txtName.setPromptText("Имя пользователя");
        txtPass.setPromptText("Пароль");

        if(DEBUG) {
            txtName.setText("Максимов Максим Николаевич");
            txtPass.setText("c82dpl17");
        }
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
            Utils.MessageBox( "Ошибка", "Ошибка","Пользователь или пароль введены неверно!",
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
