package com.maximus.dbclient;

import java.io.*;
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class DBQuery {

    private DBResult result;
    private String lastError;
    private Connection connection;
    private String queryFileName;
    private DBParam[] params;
    private String querySQL;

    public DBQuery(Connection con, String name) {
        this.connection = con;
        this.queryFileName = name;
        this.querySQL = loadFromFile();
    }

    public void setParams(DBParam[] params) {
        this.params = params;
    }

    public void setPrefix(String prefix) {
        querySQL = prefix + querySQL;
    }

    private String loadFromFile () {
        String query = "";
        String fileName = Utils.getEtcPath() + queryFileName;
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
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

    public DBResult getResult() {
        return result;
    }

    public String getLastError() {
        return lastError;
    }

    public boolean execFrom() {
        // грузим запрос из файла
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(querySQL);

            for (int i = 0; i < params.length; i++){
                if(params[i].getType() == DBValueType.DB_CHARACTER)
                    st.setString(i+1, params[i].toString());
                else if(params[i].getType() == DBValueType.DB_INT)
                    st.setInt(i+1, params[i].toInt());
                else if(params[i].getType() == DBValueType.DB_DOUBLE)
                    st.setDouble(i+1, params[i].toDouble());
                else if(params[i].getType() == DBValueType.DB_BOOLEAN)
                    st.setBoolean(i+1, params[i].toBoolean());
                else if(params[i].getType() == DBValueType.DB_DATE)
                    st.setDate(i+1, Date.valueOf(params[i].toDate()));

            }
            result = new DBResult(st.executeQuery());
            st.close();
            return true;
        }catch (SQLException ex ) {
            lastError = "SQL query exception: " + ex.toString();
            System.out.println(lastError);
            ex.printStackTrace();
        }
        return false;
    }

    public boolean execTo() {

        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(querySQL);

            for (int i = 0; i < params.length; i++){
                if(params[i].getType() == DBValueType.DB_CHARACTER)
                    st.setString(i+1, params[i].toString());
                else if(params[i].getType() == DBValueType.DB_INT)
                    st.setInt(i+1, params[i].toInt());
                else if(params[i].getType() == DBValueType.DB_DOUBLE)
                    st.setDouble(i+1, params[i].toDouble());
                else if(params[i].getType() == DBValueType.DB_BOOLEAN)
                    st.setBoolean(i+1, params[i].toBoolean());
                else if(params[i].getType() == DBValueType.DB_DATE)
                    st.setDate(i+1, Date.valueOf(params[i].toDate()));

            }
            st.executeUpdate();
            st.close();
            return true;
        }catch (SQLException ex ) {
            lastError = "SQL query exception: " + ex.toString();
            System.out.println(lastError);
            ex.printStackTrace();
        }
        return false;
    }


}

