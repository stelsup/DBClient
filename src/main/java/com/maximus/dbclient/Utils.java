package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBParam;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
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
        String out = input.trim();
        return out;
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

    public static StringBuilder buildTextArea (Map<String,String> data) {

        StringBuilder result = new StringBuilder();
        result.append("Информация: \n\n\n");
        String str = "";
        for(Map.Entry<String, String> entry : data.entrySet()) {
            //result.append( entry.getKey() +" : \t " + entry.getValue() +  "\n");
            str += String.format("%-40s%-10s%n",entry.getKey(),entry.getValue());
        }
        result.append(str);
        return result;
    }

    public static boolean parseFloatPoint (String s){
        return s.indexOf('.') != -1;
    }

    public static boolean compareEditPaymentPKValues (Object[] prevValues, Object[] newValues){
        String prevValue = prevValues[0].toString();
        String prevValue1 = prevValues[1].toString();
        String newValue = newValues[0].toString();
        String newValue1 = newValues[1].toString();
        return (prevValue.equals(newValue) && prevValue1.equals(newValue1));
    }

}

