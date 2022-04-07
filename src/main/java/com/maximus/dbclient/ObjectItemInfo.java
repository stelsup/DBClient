package com.maximus.dbclient;

/*
Описание "объекта платежа" (дом/гараж)
 */
public class ObjectItemInfo implements GroupItemInfo{
    private String name = "Объект платежа";
    private String type = "Тип";
    private String typeView = "Тип объекта: ";
    private String adressView = "Адрес: ";
    private boolean visible = true;
    private boolean enabled = true;

    public ObjectItemInfo(String name, String type, String adress){
        this.name = name;
        this.type = type;
        this.typeView += type;
        this.adressView += adress;
    }
    public String getType() {return  this.type;}

    public String getTypeView() {return this.typeView;}

    public String getAdressView() {return this.adressView;}

    @Override
    public String toString() { return this.name; }

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
