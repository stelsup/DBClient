package com.maximus.dbclient.DB;
import com.maximus.dbclient.Utils;

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

    private boolean loadProperties(){
        FileInputStream fileIn;
        propConnection = new Properties();

        try{
            String path = Utils.getEtcPath()+ propsFileName;
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
            System.out.println("Config file 'connection.properties' not loaded properly!");
            ex.printStackTrace();
        }
        return false;
    }

    private boolean connect(){

        String url = "jdbc:postgresql://" + propConnection.getProperty(propsHostName) + ":" +
                propConnection.getProperty(propsPort) + "/" + propConnection.getProperty(propsDBName);
        try {
            connection = DriverManager.getConnection(url, propConnection.getProperty(propsUserName),
                                                         propConnection.getProperty(propsPassword));
            System.out.println("Connected to DB.");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection to DB failed: " + e.toString());
            e.printStackTrace();
        }
        return false;
    }

    public DBResult getFromDB (String querySQL, DBParam[] params)
    {
        DBResult res = null;
        DBQuery qry = new DBQuery(connection, querySQL);
        if (params != null)
            qry.setParams(params);
        if(qry.exec(execType.GET))
        {
            res = qry.getResult();
        }
        else
        {
            System.out.println(qry.getLastError());
        }
        return res;
    }

    public boolean setToDB (String querySQL, DBParam[] params) {
        DBQuery qry = new DBQuery(connection, querySQL);
        if (params != null)
            qry.setParams(params);
        if(qry.exec(execType.SEND))
        {
            System.out.println("Send to DB confirm!");
            return true;
        }
        else
        {
            System.out.println(qry.getLastError());
        }
        return false;
    }

    public void disconnect() {
        if(connection != null){
            try{
                connection.close();
                System.out.println("Disconnected to DB.");
            }catch (SQLException e){
                System.out.println("Disconnection to DB failed: " + e.toString());
                e.printStackTrace();
            }
        }
    }


    public enum execType {
        GET,
        SEND
    }

}
