package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class PaymentsItemInfo implements GroupItemInfo{

    private String[] columnNames;
    private String[] values;

    public PaymentsItemInfo (Map<String, String> row) {
        this.columnNames = row.keySet().toArray(new String[0]);
        this.values = row.values().toArray(new String[0]);
    }


//    public String[] getColumnNames() {
//        return this.columnNames;
//    }

    public String[] getValues() {
        return this.values;
    }

    public String getValue(int idx) { return this.values[idx]; }
    public StringProperty getProperty(int idx) { return new SimpleStringProperty(this.values[idx]); }

//    @Override
//    public String toString() {
//
//    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSQLQuery() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
