package com.maximus.dbclient;

import com.maximus.dbclient.DB.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Controller {
    private static Controller single_instance = null;
    private String userName;
    private String querySQL;

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

    public void builderQuerySQL() {




    }




    public ArrayList<ObjectItemInfo> getObjects() {
        // todo: переделать
        querySQL = BuilderSQL.templateSELECT("objects_nameid, objects_type", "public.objects",
                "payer_name = ?", "objects_nameid", 20);
        DBParam[] obj = new DBParam[1];
        obj[0] = new DBParam("Максимов Максим Николаевич");
        DBResult res1 = DBController.getInstance().getFromDB(querySQL, obj);


        Object[] objs = res1.getValues(2);
        ArrayList<ObjectItemInfo> result = new ArrayList<ObjectItemInfo>();

        for(Object object: objs)
        {
            String userFriendlyName = object.toString();
            result.add(new ObjectItemInfo(object.toString()));
        }


        return result;
    }

    public String[] getPayersList() {

        querySQL = BuilderSQL.templateSELECT("fullname", "public.payer", "fullname");
        DBResult result = DBController.getInstance().getFromDB(querySQL, null);
        Object[] values = result.getValues("fullname");
        String[] payers = Arrays.copyOf(values, values.length, String[].class);
        return payers;
    }

    public String getPassword(String payer) {
        querySQL = BuilderSQL.templateSELECT("password_payer", "public.payer",
                "fullname = ? ","password_payer");
        DBParam[] param = new DBParam[1];
        param[0] = new DBParam(payer);
        DBResult result = DBController.getInstance().getFromDB(querySQL, param);
        String password = String.valueOf(result.getValue(0,"password_payer"));
        return password;
    }

//public boolean enterAsUser(String userName, String password)
//{
//
//}




}
