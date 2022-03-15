package com.maximus.dbclient;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class DBResult {
    private List<Map<String, String>> results = new ArrayList<Map<String, String>>();
    private int numColumns;  // кол-во столбцов
    private int lastOffset;  // возможно не нужное поле на этом уровне реализации

    public DBResult(ResultSet rs) {
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        String value = rs.getString(i);
                        value = Utils.clearDBData(value);
                        row.put(name, value);
                    }
                    results.add(row);
                }
                lastOffset = results.size();
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getCountColumns () {
        return numColumns;
    }

    public int getLastOffset () {
        return lastOffset;
    }

    public int getCountRows () {
        return results.size();
    }

    public Object getValue (int row, String nameColumn) {
        return results.get(row).get(nameColumn);
    }

    public Object[] getValues (int row ) {
        Object[] objArray = results.get(row).values().toArray();
        return objArray;
    }

    public  Map<String, String> getRow (int index) {
        return results.get(index);
    }

    public List<Map<String, String>> getAllResult () {
        return results;
    }





}