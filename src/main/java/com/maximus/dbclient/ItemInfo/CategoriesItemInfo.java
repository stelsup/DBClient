package com.maximus.dbclient.ItemInfo;

public class CategoriesItemInfo implements GroupItemInfo {

    private String name = "Категория";
    private String view = "cat_view";
    private String table = "cat_table";


    public CategoriesItemInfo (String name, String view, String table) {this.name = name; this.view = view; this.table = table;}

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() { return this.name; }

    public String getView() { return this.view; }

    public String getTable() {return this.table;}

}
