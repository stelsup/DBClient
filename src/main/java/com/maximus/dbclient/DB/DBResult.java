package com.maximus.dbclient.DB;

import com.maximus.dbclient.DiagnosticMessage;
import com.maximus.dbclient.Utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DBResult {

    private List<Map<String, String>> results = new ArrayList<>();
    private int numColumns;// кол-во столбцов
    private String[] columnTypes;


    public DBResult(ResultSet rs) {
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                numColumns = meta.getColumnCount();
                columnTypes = new String[numColumns];
                while (rs.next()) {
                    Map<String, String> row = new LinkedHashMap<>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        String value = rs.getString(i);
                        value = Utils.clearDBData(value);
                        row.put(name, value);

                        columnTypes[i-1] = meta.getColumnTypeName(i);
                    }
                    results.add(row);
                }
                rs.close();
            }
        } catch (SQLException ex) {
            DiagnosticMessage.logging("DBResult exception ", ex, this.getClass(), DiagnosticMessage.LoggerType.ERROR);
        }
    }

    public int getCountColumns () {
        return numColumns;
    }

    public int getCountRows () {
        return results.size();
    }

    public String[] getColumnTypes() { return this.columnTypes; }

    public Object getValue (int row, String nameColumn) {
        return results.get(row).get(nameColumn);
    }

    public List<Map<String, String>> getAllResult () {
        return results;
    }

    public Map<String, String> getRow (int row) {
        return results.get(row);
    }

    public Object[] getValues (int row ) {
        return results.get(row).values().toArray();
    }

    public Object[] getValues (String columnName) {
        Object[] values = new Object[results.size()];
        for(int i = 0; i < results.size(); i++){
            values[i] = results.get(i).get(columnName);
        }
        return values;
    }
}