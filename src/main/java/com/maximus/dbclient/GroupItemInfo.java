package com.maximus.dbclient;

public interface GroupItemInfo {
    public String getName();
    public String getSQLQuery();
    public boolean isEnabled();  //вкл/выкл
    public boolean isVisible();

}
