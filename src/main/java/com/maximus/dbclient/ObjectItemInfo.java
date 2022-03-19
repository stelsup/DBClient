package com.maximus.dbclient;

/*
Описание "объекта платежа" (дом/гараж)
 */
public class ObjectItemInfo implements GroupItemInfo{
    private String name = "Объект платежа";
    private boolean visible = true;
    private boolean enabled = true;

    public ObjectItemInfo(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSQLQuery() {
        return null;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }
}
