package com.maximus.dbclient;

import com.maximus.dbclient.DB.*;


import java.util.*;

public class Controller {
    private static Controller single_instance = null;
    private String userName;
    private String currentObjectName;// = "ОЗАЛРЯ52уч";
    private String currentCategory;// = "public.easy_nalog";
    private int countColumns;
    private String[] columnNames;
    private String[] columnTypes;

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
    public String getCurrentObjectName() {return this.currentObjectName;}
    public void setCurrentObjectName(String name) {this.currentObjectName = name;}
    public String getCurrentCategory() {return this.currentCategory;}
    public void setCurrentCategory(String name) {this.currentCategory = name;}
    public String[] getColumnNames() {return this.columnNames;}
    public int getCountColumns() {return this.countColumns;}
    public String getUserName()  { return this.userName; }
    //------------------------------


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
        DBParam[] param = Utils.addDBParams(payer);
        DBResult result = DBController.getInstance().getFromDB(querySQL, param);
        String password = String.valueOf(result.getValue(0,"password_payer"));
        return password;
    }

    public ArrayList<ObjectItemInfo> getObjects() {
        querySQL = BuilderSQL.templateSELECT("objects_nameid, objects_type", "public.objects",
                "payer_name = ?", "objects_nameid", 20);
        DBParam[] param = Utils.addDBParams(userName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);

        if (res == null) return null;

        ArrayList<ObjectItemInfo> result = new ArrayList<ObjectItemInfo>();
        Object[] objs = res.getValues("objects_nameid");

        for(Object object: objs)
        {
            result.add(new ObjectItemInfo(String.valueOf(object)));
        }

        return result;
    }

    public ArrayList<CategoriesItemInfo> getCategories() {
        querySQL = BuilderSQL.templateSELECT("invoice_type, invoice_view", "public.invoices",
                "object_id = ?", "invoice_type", 20);
        DBParam[] param = Utils.addDBParams(currentObjectName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);

        if (res == null) return null;

        ArrayList<CategoriesItemInfo> result = new ArrayList<>();
        Object[] objs = res.getValues("invoice_type");
        Object[] objs_tables = res.getValues("invoice_view");

        for(int i = 0; i < objs.length; i++)
        {
            result.add(new CategoriesItemInfo(String.valueOf(objs[i]), String.valueOf(objs_tables[i])));
        }

        return result;
    }

    public ArrayList<PaymentsItemInfo> getPayments() {
        querySQL = BuilderSQL.templateSELECT("*", currentCategory ,
                "obj_element = ?", "date_payment", 50);
        DBParam[] param = Utils.addDBParams(currentObjectName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);

        if (res == null) return null;

        ArrayList<PaymentsItemInfo> resultRows = new ArrayList<>();

        this.countColumns = res.getCountColumns();
        Map<String, String> row = res.getRow(0);
        this.columnNames = row.keySet().toArray(new String[0]);
        this.columnTypes = res.getColumnTypes();

        for(int i = 0; i < res.getCountRows(); i++){
            resultRows.add( new PaymentsItemInfo(res.getRow(i)));
        }

        return resultRows;

    }

    public Map<String, String> getPaymentDetails(PaymentsItemInfo payment) {
        String strCondition = "";
        for(int colIdx = 0; colIdx < columnNames.length; colIdx++) {
            String colName = columnNames[colIdx];
            strCondition += colName + " = '" + payment.getProperty(colIdx).getValue() +
                    (colIdx < (columnNames.length - 1) ? "' AND " : "' ");
        }

        querySQL = BuilderSQL.templateSELECT("*", currentCategory ,
                strCondition, "date_payment");

        DBResult res = DBController.getInstance().getFromDB(querySQL, null);

        Map<String, String> result = res.getRow(0);
        return result;
    }


}
