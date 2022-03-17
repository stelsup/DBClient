package com.maximus.dbclient;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;


public class DBController {

    private final String propsFileName = "connection.properties";
    private final String propsHostName = "hostname";
    private final String propsPort = "port";
    private final String propsDBName = "dbname";
    private final String propsUserName = "username";
    private final String propsPassword = "password";
    Properties propConnection;
    Connection connection;


    public DBController(){
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
            //Class.forName("org.postgresql.Driver");
            //Class.forName("org.postgresql.jdbc.Driver");
            //Class.forName("org.postgresql.jdbc");
            connection = DriverManager.getConnection(url, propConnection.getProperty(propsUserName),
                                                         propConnection.getProperty(propsPassword));
            System.out.println("Connected to DB.");
            return true;
        } catch (SQLException e) {
            System.out.println("Connection to DB failed: " + e.toString());
            e.printStackTrace();
        }
//        catch (ClassNotFoundException e) {
//            System.out.println("PostgreSQL JDBC driver not found!");
//            e.printStackTrace();
//        }
        return false;
    }

    public DBResult getFromDB (String queryFileName, DBParam[] params)
    {
        DBResult res = null;
        DBQuery qry = new DBQuery(connection, queryFileName);
        qry.setParams(params);
        if(qry.execFrom())
        {
            res = qry.getResult();
        }
        else
        {
            System.out.println(qry.getLastError());
        }
        return res;
    }

    public DBResult getFromDB (String queryFileName, DBParam[] params, String prefix)
    {
        DBResult res = null;
        DBQuery qry = new DBQuery(connection, queryFileName);
        qry.setParams(params);
        qry.setPrefix(prefix);
        if(qry.execFrom())
        {
            res = qry.getResult();
        }
        else
        {
            System.out.println(qry.getLastError());
        }
        return res;
    }

    public boolean setToDB (String queryFileName, DBParam[] params) {
        DBQuery qry = new DBQuery(connection, queryFileName);
        qry.setParams(params);
        if(qry.execTo())
        {
            System.out.println("Add confirm!");
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

}
