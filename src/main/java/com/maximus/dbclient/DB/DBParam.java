package com.maximus.dbclient.DB;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DBParam {
    private DBValueType type;
    private String value;

    public DBValueType getType()    { return type;}


    public DBParam(String val) { type = DBValueType.DB_CHARACTER; value = val; }
    public DBParam(Integer val) { type = DBValueType.DB_INT; value = Integer.toString(val); }
    public DBParam(Double val) { type = DBValueType.DB_DOUBLE; value = Double.toString(val); }
    public DBParam(Boolean val) { type = DBValueType.DB_BOOLEAN; value = Boolean.toString(val); }
    public DBParam(LocalDate val) {
        type = DBValueType.DB_DATE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        value = val.format(formatter);
    }


    public String toString() {
        return value;
    }

    public Integer toInt() {
        return Integer.parseInt(value);
    }

    public Double toDouble() {
        return Double.parseDouble(value);
    }

    public Boolean toBoolean() {
        return Boolean.parseBoolean(value);
    }

    public LocalDate toDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        LocalDate date = LocalDate.parse(value,formatter);
        return date;
    }
}
