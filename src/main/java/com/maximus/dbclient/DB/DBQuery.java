package com.maximus.dbclient.DB;

import com.maximus.dbclient.DiagnosticMessage;

import java.sql.*;


public class DBQuery {

    private final Connection connection;
    private final String querySQL;
    private DBResult result;
    private String lastError;
    private DBParam[] params;


    public DBQuery(Connection con, String querySQL) {
        this.connection = con;
        this.querySQL = querySQL;
    }

    public void setParams(DBParam[] params) {
        this.params = params;
    }

    public DBResult getResult() {
        return result.getCountRows() == 0 ?  null : result;
    }

    public String getLastError() {
        return lastError;
    }

    public boolean exec (DBController.execType type) {
        PreparedStatement st ;
        try {
            st = connection.prepareStatement(querySQL);

            if (params != null) {
                for (int i = 0; i < params.length; i++){

                    switch(params[i].getType()){

                        case DB_CHARACTER :
                            st.setString(i+1, params[i].toString());
                            break;
                        case DB_INT :
                            st.setInt(i+1, params[i].toInt());
                            break;
                        case DB_DOUBLE :
                            st.setDouble(i+1, params[i].toDouble());
                            break;
                        case DB_BOOLEAN :
                            st.setBoolean(i+1, params[i].toBoolean());
                            break;
                        case DB_DATE :
                            st.setDate(i+1, Date.valueOf(params[i].toDate()));

                    }
                }
            }

            ////
            System.out.println("--- QUERY START ---");
            System.out.println(st);
            System.out.println("--- QUERY END ---");
            ////

            switch (type) {
                case GET -> result = new DBResult(st.executeQuery());
                case SEND -> st.executeUpdate();
            }
            st.close();
            return true;

        }catch (SQLException ex ) {
            lastError = "SQL query exception: ";
            DiagnosticMessage.logging(lastError, ex, this.getClass(), DiagnosticMessage.LoggerType.ERROR);
        }
        return false;
    }



}

