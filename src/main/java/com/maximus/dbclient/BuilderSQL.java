package com.maximus.dbclient;

public class BuilderSQL {


    public static String templateSELECT(String target, String tableNames, String orderBy) {
        String querySQL = "SELECT " + target + " FROM " + tableNames + " ORDER BY " + orderBy + " ASC ";
        return querySQL;
    }

    public static String templateSELECT(String target, String tableNames, String condition, String orderBy) {
        String querySQL = "SELECT " + target + " FROM " + tableNames + " WHERE " + condition + " ORDER BY " + orderBy + " ASC ";
        return querySQL;
    }

    public static String templateSELECT(String target, String tableNames, String condition, String orderBy, int limit)
    {
        String querySQL = "SELECT " + target + " FROM " + tableNames + " WHERE " + condition + " ORDER BY " + orderBy + " ASC LIMIT " + limit;
        return querySQL;
    }

    public static String templateDELETE(String tableName, String condition) {
        String querySQL = "DELETE FROM " + tableName + " WHERE " + condition;
        return querySQL;
    }

    public static String templateINSERT(String tableNames, int countValues)
    {
        String querySQL = "INSERT INTO " + tableNames + " VALUES ( ";
        for(int i = 1; i < countValues; i++){
            querySQL += "?, ";
        }
        querySQL += "? )";
        return querySQL;
    }




}
