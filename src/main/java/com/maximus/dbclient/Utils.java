package com.maximus.dbclient;

public class Utils {

    public static String getApplicationPath() {
        return Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
    }

}
