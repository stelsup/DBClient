package com.maximus.dbclient;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBResult {
    private List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    private int numColumns;  // кол-во столбцов
    private int startLineIdx; // первая строка датасета
    private int endLineIdx;

    public DBResult(ResultSet rs) {
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public
}