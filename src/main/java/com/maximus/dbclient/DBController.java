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
            String path = Utils.getApplicationPath() + propsFileName;
            //String decodedPath = URLDecoder.decode(path, "UTF-8");
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
            ex.printStackTrace();
        }
        return false;
    }

    private boolean connect(){

        String url = "jdbc:postgresql://" + propConnection.getProperty(propsHostName) + ":" +
                propConnection.getProperty(propsPort) + "/" + propConnection.getProperty(propsDBName);
        try {
            Class.forName("com.postgresql.jdbc.Driver");
            connection = DriverManager.getConnection(url, propConnection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found!");
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet loadPaymentCategories(String person)
    {
        ResultSet res = null;
//        DBQuery qry = new DBQuery_loadswjhwsikjhf(connection);
        DBQuery qry = new DBQuery(connection, "loadCategories.sql");
        //qry.setParams(person);
        if(qry.exec())
        {
            res = qry.getResult();
        }
        else
        {
            ///qry.getLastError();
        }
        return res;
    }

}
