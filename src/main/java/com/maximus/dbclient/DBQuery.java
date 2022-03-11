package com.maximus.dbclient;

import java.io.*;
import java.sql.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;


public class DBQuery {

    private ResultSet result;
    private String lastError;
    private Connection connection;
    private String queryFileName;
    private DBParam[] params;


    public DBQuery(Connection con, String name) {
        this.connection = con;
        this.queryFileName = name;
    }

    private String loadFromFile () {
        String query = "";
        String fileName = Utils.getApplicationPath() + "../../etc/" + queryFileName;
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

    public ResultSet getResult() {
        return result;
    }
    public void setParams(DBParam[] params) {
        this.params = params;
    }
    public String getLastError() {
        return lastError;
    }

    public boolean exec() {
        // грузим запрос из файла
        String query = loadFromFile();

        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(query);

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
            result = st.executeQuery();
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

