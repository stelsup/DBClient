package com.maximus.dbclient.DB;
import com.maximus.dbclient.DiagnosticMessage;
import com.maximus.dbclient.Utils;
import javafx.scene.control.Alert;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class DBController {
    private static DBController single_instance = null;
    private final String propsFileName = "connection.properties";
    private final String propsHostName = "hostname";
    private final String propsPort = "port";
    private final String propsDBName = "dbname";
    private final String propsUserName = "username";
    private final String propsPassword = "password";
    Properties propConnection;
    Connection connection;

    public static DBController getInstance()
    {
        if (single_instance == null)
            single_instance = new DBController();
        return single_instance;
    }

    private DBController(){
        loadProperties();
        connect();
    }

    public enum execType {
        GET,
        SEND
    }

    private boolean loadProperties(){
        FileInputStream fileIn;
        propConnection = new Properties();
        String path = Utils.getEtcPath()+ propsFileName;

        try{
            fileIn = new FileInputStream(path);
            propConnection.load(fileIn);

            if(propConnection.getProperty(propsHostName) != null
                && propConnection.getProperty(propsPort) != null
                && propConnection.getProperty(propsDBName) != null
                && propConnection.getProperty(propsUserName) != null
                && propConnection.getProperty(propsPassword) != null) {
                return true;
            }
        }catch(IOException ex) {
            DiagnosticMessage.logging("Config file 'connection.properties' not loaded properly!", ex, this.getClass(), DiagnosticMessage.LoggerType.ERROR);
            Utils.MessageBox( "Ошибка", "Ошибка","Конфигурационный файл 'connection.properties' не загружен из '" + path + "'!",
                    Alert.AlertType.WARNING, null);
        }
        return false;
    }

    private boolean connect(){

        String url = "jdbc:postgresql://" + propConnection.getProperty(propsHostName) + ":" +
                propConnection.getProperty(propsPort) + "/" + propConnection.getProperty(propsDBName);
        try {
            connection = DriverManager.getConnection(url, propConnection.getProperty(propsUserName),
                                                         propConnection.getProperty(propsPassword));

            DiagnosticMessage.logging("Connected to DB.", null, this.getClass(), DiagnosticMessage.LoggerType.INFO);
            return true;
        } catch (SQLException e) {
            DiagnosticMessage.logging("Connection to DB failed: ", e, this.getClass(), DiagnosticMessage.LoggerType.ERROR);

            Utils.MessageBox( "Ошибка", "Нет соединения","Отсутствует подключение к базе данных: " + e,
                    Alert.AlertType.WARNING, null);
        }
        return false;
    }

    public DBResult getFromDB (String querySQL, DBParam[] params) {
        checkConnection();
        DBResult res = null;
        DBQuery qry = new DBQuery(connection, querySQL);
        if (params != null)
            qry.setParams(params);
        if(qry.exec(execType.GET)) {
            res = qry.getResult();
        }
        else {
            System.out.println(qry.getLastError());
        }
        return res;
    }

    public boolean setToDB (String querySQL, DBParam[] params) {
        checkConnection();
        DBQuery qry = new DBQuery(connection, querySQL);
        if (params != null)
            qry.setParams(params);
        if(qry.exec(execType.SEND)) {
            DiagnosticMessage.logging("Send to DB confirm!", null, this.getClass(), DiagnosticMessage.LoggerType.INFO);
            return true;
        }
        else {
            System.out.println(qry.getLastError());
        }
        return false;
    }

    public void checkConnection() {
        try {
            if(connection == null || !connection.isValid(240)){
                this.connect();
            }
        } catch (SQLException e) {
            DiagnosticMessage.logging("CheckConnection exception ", e, this.getClass(), DiagnosticMessage.LoggerType.ERROR);
        }
    }

    public void disconnect() {
        if(connection != null){
            try{
                connection.close();
                DiagnosticMessage.logging("Disconnected to DB.", null, this.getClass(), DiagnosticMessage.LoggerType.INFO);
            }catch (SQLException e){
                DiagnosticMessage.logging("Disconnection to DB failed: ", e, this.getClass(), DiagnosticMessage.LoggerType.ERROR);
            }
        }
    }

}
