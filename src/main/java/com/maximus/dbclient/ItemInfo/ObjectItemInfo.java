package com.maximus.dbclient.ItemInfo;


public class ObjectItemInfo implements GroupItemInfo{
    private String name = "Объект платежа";
    private String type = "Тип";
    private String typeView = "Тип объекта: ";
    private String adressView = "Адрес: ";

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

}
