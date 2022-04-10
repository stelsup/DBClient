package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBParam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;

public class Utils {

    public static String getEtcPath() {
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return path + "../../etc/";
    }

    public static String getImagesPath(){
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return path + "../../images/";
    }

    public static String clearDBData(String input) {

        return input == null ? "" : input.trim();
    }

    public static GUIWindow showWindow(String fxmlName, GUIParam guiParam, int minHeight, int minWidth, String title) throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(fxmlName));
        stage.setTitle(title);

        stage.getIcons().add(new Image("file://" + getImagesPath() + "general_secur.png"));
        Scene scene = new Scene(fxmlLoader.load()/*, 320, 240*/);
        stage.initModality(guiParam.modality);
        stage.initOwner(guiParam.ownerParent);

        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);
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

    public static ButtonType MessageBox(String title, String text, String textDetails, Alert.AlertType type, ButtonType[] buttons) {
        Alert alert = new Alert(type);
        Stage dialog = (Stage) alert.getDialogPane().getScene().getWindow();
        dialog.getIcons().add(new Image("file://" + getImagesPath() + "general_secur.png"));
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.setContentText(textDetails);

        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                alert.close();
            }else{
                alert.close();
            }
        });
        return alert.getResult();
    }

    public static String loadFromFile(String fileName) {
        String query = "";
        String path = Utils.getEtcPath() + fileName;
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(path));
            while (buffReader.ready())
            {
                query += buffReader.readLine();
            }
            buffReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return query;
    }

    public static Image loadObjImage(String name){
        switch(name){
            case "Квартира":
                return new Image("file://" + Utils.getImagesPath() + "house.png");

            case "Дом":
                return new Image("file://" + Utils.getImagesPath() + "cottege.png");

            case "Участок":
                return new Image("file://" + Utils.getImagesPath() + "zabor.png");

            case "Транспорт":
                return new Image("file://" + Utils.getImagesPath() + "car.png");

            default:
                return new Image("file://" + Utils.getImagesPath() + "easy_home.png");

        }
    }

    public static Image loadCatImage(String name){

        switch (name){
            case "Налог" :
                return new Image("file://" + Utils.getImagesPath() + "nalog.png");

            case "Электричество":
                return new Image("file://" + Utils.getImagesPath() + "energy_yellow.png");

            case "ТКО":
                return new Image("file://" + Utils.getImagesPath() + "tko.png");

            case "ХВС":
                return new Image("file://" + Utils.getImagesPath() + "cold_water.png");

            case "Газ":
                return new Image("file://" + Utils.getImagesPath() + "gas.png");

            case "Кооператив":
                return new Image("file://" + Utils.getImagesPath() + "cooperative.png");

            case "Охрана", "Охрана2":
                return new Image("file://" + Utils.getImagesPath() + "general_secur.png");

            default:
                return new Image("file://" + Utils.getImagesPath() + "inspection.png");
        }
    }

    // todo Объеденить все loadImage в один, с добавлением размеров

    public static ImageView loadDOWTollBarImage(int index){
        Image image;

        switch(index){
            case 1:
                image = new Image("file://" + Utils.getImagesPath() + "add.png");
                break;
            case 2:
                image = new Image("file://" + Utils.getImagesPath() + "delete.png");
                break;
            case 3:
                image = new Image("file://" + Utils.getImagesPath() + "edit.png");
                break;
            case 4:
                image = new Image("file://" + Utils.getImagesPath() + "search.png");
                break;
            default:
                return null;

        }
        ImageView picture = new ImageView(image);
        picture.setFitWidth(18.0);
        picture.setFitHeight(18.0);
        return picture;
    }

    public static ImageView loadUPTollBarImage(int index) {
        Image image;

        switch(index){
            case 1:
                image = new Image("file://" + Utils.getImagesPath() + "arrow_left.png");
                break;
            case 2:
                image = new Image("file://" + Utils.getImagesPath() + "arrow_right.png");
                break;
            case 3:
                image = new Image("file://" + Utils.getImagesPath() + "search.png");
                break;
            case 4:
                image = new Image("file://" + Utils.getImagesPath() + "refresh.png");
                break;
            default:
                return null;

        }
        ImageView picture = new ImageView(image);
        picture.setFitWidth(25.0);
        picture.setFitHeight(25.0);
        return picture;
    }

    public static String trimSpaces(String input) {
        return input.replaceAll(" ","");
    }

    public static DBParam[] addDBParams (Object ... param) {
        DBParam[] params = new DBParam[param.length];
        for (int i = 0; i < params.length; i++) {
            if(param[i] instanceof String){
                params[i] = new DBParam((String)param[i]);
            }else if(param[i] instanceof Integer){
                params[i] = new DBParam((Integer) param[i]);
            }else if(param[i] instanceof Double){
                params[i] = new DBParam((Double) param[i]);
            }else if(param[i] instanceof Boolean){
                params[i] = new DBParam((Boolean) param[i]);
            }else if(param[i] instanceof LocalDate){
                params[i] = new DBParam((LocalDate) param[i]);
            }
        }
        return params;
    }

    public static StringBuilder buildTextArea (Map<String,String> data, String[] friendlyNames) {

        StringBuilder result = new StringBuilder();
        result.append("Информация: \n\n\n");
        String str = "";
        String[] dataStr = data.values().toArray(new String[0]);
        for(int i = 0; i < friendlyNames.length; i++) {
            str += String.format("%-40s%-10s%n",friendlyNames[i] + ": ", dataStr[i]);
        }
        result.append(str);
        return result;
    }

    public static boolean parseFloatPoint (String s){
        return s.indexOf('.') != -1;
    }

    public static String passToHash(String rawPass) {
        String stringHash = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawPass.getBytes());
            stringHash = new String(messageDigest.digest());
        } catch (NoSuchAlgorithmException ex) {
            //
        }
        return stringHash;
    }

}

