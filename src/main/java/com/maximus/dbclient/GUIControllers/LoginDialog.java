package com.maximus.dbclient.GUIControllers;

import com.maximus.dbclient.Controller;
import com.maximus.dbclient.DB.DBController;
import com.maximus.dbclient.DiagnosticMessage;
import com.maximus.dbclient.GUICore.GUIController;
import com.maximus.dbclient.GUICore.GUIParam;
import com.maximus.dbclient.GUICore.GUIWindow;
import com.maximus.dbclient.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;


import static com.maximus.dbclient.Utils.showWindow;


public class LoginDialog extends GUIController {

    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField txtPass;
    @FXML
    private ImageView imgUser;
    @FXML
    private ImageView imgPass;
    @FXML
    private Hyperlink lnkRegister;

    public ButtonType btnResult = ButtonType.CANCEL;
    public String[] strCredentials  = new String[2];
    private String userName;

    public void onShow() {
        Stage thisStage = ((Stage) super.scene.getWindow());
        thisStage.setMaxHeight(270);
        thisStage.setMaxWidth(350);

       Image user = new Image(Utils.getImagesPath() + "user_pluse.png");
       Image pass = new Image(Utils.getImagesPath() + "pass_green.png");

       imgUser.setImage(user);
       imgPass.setImage(pass);

        txtName.setPromptText("Имя пользователя");
        txtPass.setPromptText("Пароль");
    }

    @FXML
    private void btnOKClick(ActionEvent event) {

        strCredentials[0] = txtName.getText();
        strCredentials[1] = Utils.passToHash(txtPass.getText());

        if (checkCredentials()) {
            btnResult = ButtonType.OK;
            Controller.getInstance().setUserName(userName);
            this.closeWindow();
        }else {
            ButtonType[] buttons = new ButtonType[2];
            buttons[0] = ButtonType.OK;
            buttons[1] = ButtonType.CANCEL;
            DiagnosticMessage.logging("Incorrect user or password ", null, this.getClass(), DiagnosticMessage.LoggerType.WARN);
            Utils.MessageBox( "Ошибка", "Ошибка","Пользователь или пароль введены неверно!",
                    Alert.AlertType.WARNING, buttons);
        }

    }

    @FXML
    private void btnCancelCLick(ActionEvent event) {
        btnResult = ButtonType.CANCEL;
        this.closeWindow();
    }

    @FXML
    private void lnkRegisterClick() throws IOException {
        GUIWindow registrationWindow = showWindow("RegistrationDialog.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT,430,500),"Регистрация");
        RegistrationDialog regDialog = (RegistrationDialog)registrationWindow.getController();

        Object[] regData = regDialog.getAddData();
        if (regData == null) return;
        if(!checkName(Controller.getInstance().getPayersList(), String.valueOf(regData[0]))) {
            Controller.getInstance().addUser(regData);
        } else {
            DiagnosticMessage.logging("Registration failed: user already exist ", null, this.getClass(), DiagnosticMessage.LoggerType.WARN);
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Пользователь с такими инициалами уже существует!",
                    Alert.AlertType.WARNING, null);
        }
    }

    public ButtonType getBtnResult() {
        return this.btnResult;
    }


    private boolean checkCredentials() {

        if (!checkName(Controller.getInstance().getPayersList(), strCredentials[0])){
            return false;
        }
        return checkPass(Controller.getInstance().getPassword(userName));

    }


    private boolean checkName(String[] payers, String name) {

        String userName = Utils.trimSpaces(name);
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
