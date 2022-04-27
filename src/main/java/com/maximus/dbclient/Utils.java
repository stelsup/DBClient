package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBController;
import com.maximus.dbclient.DB.DBParam;
import com.maximus.dbclient.GUICore.GUIController;
import com.maximus.dbclient.GUICore.GUIParam;
import com.maximus.dbclient.GUICore.GUIWindow;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Map;

public class Utils {

    public static String getEtcPath() {
        String path = System.getProperty("user.dir");
        return path + "/../etc/";

    }

    public static String getImagesPath(){
        String path = System.getProperty("user.dir");
        return "file:" + path + "/../images/";
    }

//    public static void setLogPath() {
//        System.setProperty("TalendJob.log","C:\\coucou2.log");
//        LoggerContext ctx =(org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
//        ctx.reconfigure();
//    }

    public static String clearDBData(String input) {

        return input == null ? "" : input.trim();
    }

    public static GUIWindow showWindow(String fxmlName, GUIParam guiParam, String title) throws IOException
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource(fxmlName));
        stage.setTitle(title);

        stage.getIcons().add(new Image(getImagesPath() + "general_secur.png"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initModality(guiParam.modality);
        stage.initOwner(guiParam.ownerParent);

        stage.setMinHeight(guiParam.minHeight);
        stage.setMinWidth(guiParam.minWidth);
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
        dialog.getIcons().add(new Image(getImagesPath() + "general_secur.png"));
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

    public static ImageView loadObjImage(String name, double width, double height){
        Image image;

        switch(name){
            case "Квартира":
                image = new Image(Utils.getImagesPath() + "house.png");
                break;
            case "Дом":
                image = new Image(Utils.getImagesPath() + "cottege.png");
                break;
            case "Участок":
                image = new Image(Utils.getImagesPath() + "zabor.png");
                break;
            case "Транспорт":
                image = new Image(Utils.getImagesPath() + "car.png");
                break;
            default:
                image = new Image(Utils.getImagesPath() + "easy_home.png");

        }
        ImageView picture = new ImageView(image);
        picture.setFitWidth(width);
        picture.setFitHeight(height);
        return  picture;
    }

    public static ImageView loadCatImage(String name, double width, double height){
        Image image;

        switch (name){
            case "Налог" :
                image = new Image(Utils.getImagesPath() + "nalog.png");
                break;
            case "Электричество":
                image = new Image(Utils.getImagesPath() + "energy_yellow.png");
                break;
            case "ТКО":
                image = new Image(Utils.getImagesPath() + "tko.png");
                break;
            case "ХВС":
                image = new Image(Utils.getImagesPath() + "cold_water.png");
                break;
            case "Газ":
                image = new Image(Utils.getImagesPath() + "gas.png");
                break;
            case "Кооператив":
                image = new Image(Utils.getImagesPath() + "cooperative.png");
                break;
            case "Охрана", "Охрана2":
                image = new Image(Utils.getImagesPath() + "general_secur.png");
                break;
            default:
                image = new Image(Utils.getImagesPath() + "inspection.png");
        }

        ImageView picture = new ImageView(image);
        picture.setFitWidth(40);
        picture.setFitHeight(40);

        return picture;
    }

    public static ImageView loadToolsImage(String name, double width, double height){
        Image image;

        switch(name){
            case "add" :
                image = new Image(Utils.getImagesPath() + "add.png");
                break;
            case "delete" :
                image = new Image(Utils.getImagesPath() + "delete.png");
                break;
            case "edit" :
                image = new Image(Utils.getImagesPath() + "edit.png");
                break;
            case "search" :
                image = new Image(Utils.getImagesPath() + "search.png");
                break;
            case "arrow_left":
                image = new Image(Utils.getImagesPath() + "arrow_left.png");
                break;
            case "arrow_right":
                image = new Image(Utils.getImagesPath() + "arrow_right.png");
                break;
            case "refresh":
                image = new Image(Utils.getImagesPath() + "refresh.png");
                break;
            default:
                return null;
        }

        ImageView picture = new ImageView(image);
        picture.setFitWidth(width);
        picture.setFitHeight(height);
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
            DiagnosticMessage.logging("Hashing failed: ", ex, Utils.class, DiagnosticMessage.LoggerType.ERROR);
        }
        return stringHash;
    }


}

