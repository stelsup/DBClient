package com.maximus.dbclient;

public class CategoriesItemInfo implements GroupItemInfo {

    private String name = "Категория";
    private boolean visible = true;
    private boolean enabled = true;


    public CategoriesItemInfo (String name) {this.name = name;}

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
        return false;
    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
