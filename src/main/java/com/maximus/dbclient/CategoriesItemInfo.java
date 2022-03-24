package com.maximus.dbclient;

public class CategoriesItemInfo implements GroupItemInfo {

    private String name = "Категория";
    private String view = "cat_view";
    private boolean visible = true;
    private boolean enabled = true;


    public CategoriesItemInfo (String name, String view) {this.name = name; this.view = view;}

    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String toString() { return this.name; }

    //@Override
    public String getView() { return this.view; }

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
