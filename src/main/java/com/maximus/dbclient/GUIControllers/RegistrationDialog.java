package com.maximus.dbclient.GUIControllers;

import com.maximus.dbclient.GUICore.GUIController;
import com.maximus.dbclient.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class RegistrationDialog extends GUIController {

    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField txtFIO;
    @FXML
    private DatePicker txtDate;
    @FXML
    private TextField txtAdress;
    @FXML
    private TextField txtPhone;
    @FXML
    private PasswordField txtPass;
    @FXML
    private PasswordField txtRePass;

    private Object[] addData;


    @Override
    public void onShow() {
        Stage thisStage = ((Stage) super.scene.getWindow());
        thisStage.setMaxHeight(430);
        thisStage.setMaxWidth(500);

        txtDate.setEditable(false);


       TextFormatter<String> TELEPHONE;
        UnaryOperator<TextFormatter.Change> filter;
        filter = change -> {
            String str = change.getControlNewText();
            if(Pattern.matches("[0-9]+",str)) {
                return change;
            }
            return null;
        };
        TELEPHONE = new TextFormatter<String>(filter);//можно задать значение по умолчанию
        txtPhone.setTextFormatter(TELEPHONE);



    }

    public void btnOkClick() {
        String strFio = txtFIO.getText();
        LocalDate date = txtDate.getValue();
        String strAdress = txtAdress.getText();
        String strPhone = txtPhone.getText();
        String strPass = txtPass.getText();
        String strPass2 = txtRePass.getText();

        if(strFio.length() > 80 || strFio.isEmpty()) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Введите значение не более 80 символов в поле: ФИО",
                    Alert.AlertType.WARNING, null);
            return;
        } else if(date == null) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Выберите дату рождения в поле: Дата",
                    Alert.AlertType.WARNING, null);
            return;
        } else if(strAdress.length() > 100 || strAdress.isEmpty()) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Введите значение не более 100 символов в поле: Адрес",
                    Alert.AlertType.WARNING, null);
            return;
        } else if(strPhone.length() > 30 || strPhone.isEmpty()) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Введите значение не более 30 символов в поле: Телефон",
                    Alert.AlertType.WARNING, null);
            return;
        }else if(strPass.isEmpty()) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Введите пароль пользователя.",
                    Alert.AlertType.WARNING, null);
            return;
        }else if(!strPass.equals(strPass2)) {
            Utils.MessageBox( "Ошибка", "Регистрация невозможна!","Введенные пароли не совпадают!",
                    Alert.AlertType.WARNING, null);
            return;
        }
        //----------------
        Object[] data = new Object[5];
        data[0] = strFio;
        data[1] = date;
        data[2] = strAdress;
        data[3] = strPhone;
        data[4] = Utils.passToHash(strPass);

        this.addData = data;
        super.closeWindow();
    }

    public void btnCancelCLick() {
        addData = null;
        this.closeWindow();
    }


    public Object[] getAddData () {return this.addData;}
}
