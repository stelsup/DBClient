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
    private String[] params;


    public DBQuery(Connection con, String name) {
        this.connection = con;
        this.queryFileName = name;
    }

    private String loadFromFile () {
        String query = null;
        String fileName = Utils.getApplicationPath() + queryFileName;
        try {
            BufferedReader buffReader = new BufferedReader(new FileReader(fileName));
            while (buffReader.ready())
            {
                query += buffReader.readLine();
            }

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
    public void setParams(String[] params) {
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


            //st.set


            //result = st.executeUpdate();
            return true;
        }catch (SQLException ex ) {
            ex.printStackTrace();
        }
        return false;
    }

}
