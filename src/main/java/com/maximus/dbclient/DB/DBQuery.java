package com.maximus.dbclient.DB;

import java.sql.*;


public class DBQuery {

    private DBResult result;
    private String lastError;
    private Connection connection;
    private DBParam[] params;
    private String querySQL;

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

    public boolean exec(DBController.execType type) {
        PreparedStatement st = null;
        try {
            st = connection.prepareStatement(querySQL);

            if (params != null) {
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
            lastError = "SQL query exception: " + ex.toString();
            System.out.println(lastError);
            ex.printStackTrace();
        }
        return false;
    }



}

