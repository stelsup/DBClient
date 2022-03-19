package com.maximus.dbclient;

import java.util.ArrayList;

public class Controller {
    private static Controller single_instance = null;
    private String userName;

    private Controller() {
        ;//
    }

    public static Controller getInstance()
    {
        if (single_instance == null)
            single_instance = new Controller();
        return single_instance;
    }
    //------------------------------
    public void setUserName(String name) { this.userName = name; }
    public String getUserName()  { return this.userName; }
    //------------------------------

    public ArrayList<ObjectItemInfo> getObjects() {
        // todo: переделать
        DBParam[] obj = new DBParam[1];
        obj[0] = new DBParam("Максимов Максим Николаевич");
        DBResult res1 = DBController.getInstance().getFromDB("loadObjects.txt", obj);


        Object[] objs = res1.getValues(2);
        ArrayList<ObjectItemInfo> result = new ArrayList<ObjectItemInfo>();

        for(Object object: objs)
        {
            String userFriendlyName = object.toString();
            result.add(new ObjectItemInfo(object.toString()));
        }


        return result;
    }


    //------------------------------
//public String[] getPayersList()
//{
//
//}

//public boolean enterAsUser(String userName, String password)
//{
//
//}




}
