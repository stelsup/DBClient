package com.maximus.dbclient;

public class Utils {

    public static String getEtcPath() {
        String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        return path + "../../etc/";
    }

    public static String clearDBData(String input) {
        String out = input.trim();
        return out;
    }

}
