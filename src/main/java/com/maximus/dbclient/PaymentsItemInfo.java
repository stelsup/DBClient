package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

public class PaymentsItemInfo implements GroupItemInfo{

    private StringProperty str1;
    private StringProperty str2;
    private StringProperty str3;
    private StringProperty str4;
    private StringProperty str5;
    private StringProperty str6;
    private StringProperty str7;
    private StringProperty str8;
    private StringProperty str9;
    private StringProperty str10;
    private StringProperty str11;
    private StringProperty str12;
    private StringProperty str13;
    private StringProperty str14;
    private StringProperty str15;
    private StringProperty str16;
    private StringProperty str17;
    private StringProperty str18;
    private StringProperty str19;
    private StringProperty str20;

    public PaymentsItemInfo (Map<String, String> row) {
        //this.columnNames = row.keySet().toArray(new String[0]);
        //this.values = row.values().toArray(new String[0]);
        String[] columnNames = row.keySet().toArray(new String[0]);
        String[] strValuesArray = row.values().toArray(new String[0]);
        for(int idx = 0; idx < strValuesArray.length; idx++) {
            if(idx == 0)  str1 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 1)  str2 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 2)  str3 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 3)  str4 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 4)  str5 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 5)  str6 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 6)  str7 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 7)  str8 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 8)  str9 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 9)  str10 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 10)  str11 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 11)  str12 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 12)  str13 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 13)  str14 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 14)  str15 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 15)  str16 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 16)  str17 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 17)  str18 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 18)  str19 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
            if(idx == 19)  str20 = new SimpleStringProperty(this, columnNames[idx], strValuesArray[idx]);
        }
    }

    public StringProperty getProperty(int idx) {
        if(idx == 0) return str1;
        if(idx == 1) return str2;
        if(idx == 2) return str3;
        if(idx == 3) return str4;
        if(idx == 4) return str5;
        if(idx == 5) return str6;
        if(idx == 6) return str7;
        if(idx == 7) return str8;
        if(idx == 8) return str9;
        if(idx == 9) return str10;
        if(idx == 10) return str11;
        if(idx == 11) return str12;
        if(idx == 12) return str13;
        if(idx == 13) return str14;
        if(idx == 14) return str15;
        if(idx == 15) return str16;
        if(idx == 16) return str17;
        if(idx == 17) return str18;
        if(idx == 18) return str19;
        if(idx == 19) return str20;
        return new SimpleStringProperty("");
    }

//    public String getString(int idx) {
//        if(idx == 0) return str1.getValue();
//        if(idx == 1) return str2;
//        if(idx == 2) return str3;
//        if(idx == 3) return str4;
//        if(idx == 4) return str5;
//        if(idx == 5) return str6;
//        if(idx == 6) return str7;
//        if(idx == 7) return str8;
//        if(idx == 8) return str9;
//        if(idx == 9) return str10;
//        if(idx == 10) return str11;
//        if(idx == 11) return str12;
//        if(idx == 12) return str13;
//        if(idx == 13) return str14;
//        if(idx == 14) return str15;
//        if(idx == 15) return str16;
//        if(idx == 16) return str17;
//        if(idx == 17) return str18;
//        if(idx == 18) return str19;
//        if(idx == 19) return str20;
//        return new SimpleStringProperty("");
//    }


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
