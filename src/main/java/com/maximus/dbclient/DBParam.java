package com.maximus.dbclient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DBParam {
    private DBValueType type;
    private String value;

    public DBValueType getType()    { return type;}

    DBParam(String val) { type = DBValueType.DB_CHARACTER; value = val; }
    DBParam(Integer val) { type = DBValueType.DB_INT; value = Integer.toString(val); }
    DBParam(Double val) { type = DBValueType.DB_DOUBLE; value = Double.toString(val); }
    DBParam(Boolean val) { type = DBValueType.DB_BOOLEAN; value = Boolean.toString(val); }
    DBParam(LocalDate val) {
        type = DBValueType.DB_DATE;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        value = val.format(formatter);
    }


//    static DBParam fromString(String val)
//    {
//        return DBParam(String val);
//    }

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