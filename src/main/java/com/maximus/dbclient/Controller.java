package com.maximus.dbclient;

import com.maximus.dbclient.DB.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;


import java.util.*;

public class Controller {
    private static Controller single_instance = null;
    private String userName;
    private PageArea currentPageArea;
    private String currentObjectName;
    private String currentCategory;
    private String generalCatTable;
    private int countColumns;
    private String[] friendlyViewNames;
    private String[] friendlyTableNames;
    private String[] columnNames;
    private String[] columnTypes;
    private String[] genTableColNames;
    private String[] genTableColTypes;
    private PaymentsItemInfo currentPayment;
    private Map<String, String> currentPaymentDetails;

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
    public PageArea getCurrentPageArea() { return this.currentPageArea; }
    public String getCurrentObjectName() {return this.currentObjectName;}
    public void setCurrentObjectName(String name) {this.currentObjectName = name;}
    public String getCurrentCategory() {return this.currentCategory;}
    public void setCurrentCategory(String name) {this.currentCategory = name;}
    public String getGeneralTable() {return this.generalCatTable;}
    public void setGeneralTable(String name) {this.generalCatTable = name;}
    public String[] getFriendlyViewNames() {return this.friendlyViewNames;}
    public void setFriendlyViewNames(String[] names) {this.friendlyViewNames = names;}
    public String[] getFriendlyTableNames() {return this.friendlyTableNames;}
    public void setFriendlyTableNames(String[] names) {this.friendlyTableNames = names;}
    public String[] getColumnNames() {return this.columnNames;}
    public String[] getColumnTypes() {return this.columnTypes;}
    public String[] getGenTableColNames() {return  this.genTableColNames;}
    public String[] getGenTableColTypes() {return  this.genTableColTypes;}
    public int getCountColumns() {return this.countColumns;}
    public String getUserName()  { return this.userName; }
    public void setCurrentPayment(PaymentsItemInfo payment) {this.currentPayment = payment;}
    public PaymentsItemInfo getCurrentPayment() {return currentPayment;}
    public Map<String, String> getCurrentPaymentDetails() {return currentPaymentDetails;}
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
        querySQL = BuilderSQL.templateSELECT("objects_nameid, objects_type, objects_adress", "public.objects",
                "payer_name = ?", "objects_nameid", 20);
        DBParam[] param = Utils.addDBParams(userName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);

        if (res == null) return null;

        ArrayList<ObjectItemInfo> result = new ArrayList<ObjectItemInfo>();
        Object[] objsName = res.getValues("objects_nameid");
        Object[] objsType = res.getValues("objects_type");
        Object[] objsAdress = res.getValues("objects_adress");

        for(int i = 0; i < objsName.length; i++)
        {
            result.add(new ObjectItemInfo(String.valueOf(objsName[i]), String.valueOf(objsType[i]), String.valueOf(objsAdress[i])));
        }

        return result;
    }

    public ArrayList<CategoriesItemInfo> getCategories() {
        querySQL = BuilderSQL.templateSELECT("invoice_type, invoice_view, invoice_table", "public.invoices",
                "object_id = ?", "invoice_type", 20);
        DBParam[] param = Utils.addDBParams(currentObjectName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);

        if (res == null) return null;

        ArrayList<CategoriesItemInfo> result = new ArrayList<>();
        Object[] objs = res.getValues("invoice_type");
        Object[] objs_view = res.getValues("invoice_view");
        Object[] objs_table = res.getValues("invoice_table");

        for(int i = 0; i < objs.length; i++)
        {
            result.add(new CategoriesItemInfo(String.valueOf(objs[i]), String.valueOf(objs_view[i]), String.valueOf(objs_table[i])));
        }

        return result;
    }

    public ArrayList<PaymentsItemInfo> getPayments(int offset, int limit) {
        querySQL = BuilderSQL.templateSELECT("*", currentCategory ,
                "obj_element = ?", "date_payment", offset, limit);
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

    public ArrayList<PaymentsItemInfo> searchPayment(String searchStr) {
        String strCondition = "";
        for(int colIdx = 0; colIdx < columnNames.length; colIdx++) {
            String colName = columnNames[colIdx];

            switch (columnTypes[colIdx])
            {
                case "numeric" :
                    strCondition += "to_char(" + colName + ", 'FM999999999999999999') LIKE '%" + searchStr + "%' OR ";
                    break;
                case "date" :
                    strCondition += "to_char(" + colName + ", 'YYYY-MM-DD') LIKE '%" + searchStr + "%' OR ";
                    break;
                case "boolean", "bool" :
                    break;
                case "smallint" :
                    strCondition += "to_char(" + colName + ", '999') LIKE '%" + searchStr + "%' OR ";
                    break;
                default:
                    strCondition += "LOWER(" + colName + ") LIKE LOWER('%" + searchStr + "%') OR ";
                   break;
            }
        }
        // откусим OR от конца
        strCondition = strCondition.substring(0, strCondition.length()-3);

        querySQL = BuilderSQL.templateSELECT("*", currentCategory,
                strCondition, 50);

        DBResult res = DBController.getInstance().getFromDB(querySQL, null);

        if (res == null) return null;

        ArrayList<PaymentsItemInfo> resultRows = new ArrayList<>();
        for(int i = 0; i < res.getCountRows(); i++){
            resultRows.add( new PaymentsItemInfo(res.getRow(i)));
        }

        return resultRows;
    }

    public Map<String, String> getPaymentDetails(PaymentsItemInfo payment) {

        this.currentPayment = payment;
        String strCondition = "";
        for(int colIdx = 0; colIdx < columnNames.length; colIdx++) {
            String colName = columnNames[colIdx];
            strCondition += colName + " = '" + currentPayment.getProperty(colIdx).getValue() +
                    (colIdx < (columnNames.length - 1) ? "' AND " : "' ");
        }

        querySQL = BuilderSQL.templateSELECT("*", generalCatTable ,
                strCondition, "date_payment");

        DBResult res = DBController.getInstance().getFromDB(querySQL, null);

        Map<String, String> result = res.getRow(0);
        this.currentPaymentDetails = result;


        return result;
    }


    public void setGeneralTableParams(String tableName) {
        this.generalCatTable = tableName;

        querySQL = BuilderSQL.templateSELECT("column_name, data_type",
                "INFORMATION_SCHEMA.COLUMNS","table_name = ? ",50);
        DBParam[] param = Utils.addDBParams(generalCatTable);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);
        Object[] obj1  = res.getValues("column_name");
        Object[] obj2  = res.getValues("data_type");
        this.genTableColNames = Arrays.copyOf(obj1, obj1.length, String[].class);
        this.genTableColTypes = Arrays.copyOf(obj2, obj2.length, String[].class);

    }

    public void addPayment(Object[] data){

        querySQL = BuilderSQL.templateINSERT(generalCatTable,genTableColNames.length);
        DBParam[] param = Utils.addDBParams(data);
        boolean res = DBController.getInstance().setToDB(querySQL,param);

    }

    public void  deleteCurrentPayment(){

        if(this.currentPayment == null)
            return;

        String strCondition = "";
        for(int colIdx = 0; colIdx < columnNames.length; colIdx++) {
            String colName = columnNames[colIdx];
            strCondition += colName + " = '" + this.currentPayment.getProperty(colIdx).getValue() +
                    (colIdx < (columnNames.length - 1) ? "' AND " : "' ");
        }

        querySQL = BuilderSQL.templateDELETE(generalCatTable, strCondition);
        boolean res = DBController.getInstance().setToDB(querySQL, null);

        if(res){
            this.currentPayment = null;
        }else {
            System.out.println("Delete from DB false");
        }
    }

    public void editCurrentPayment(Object[] data){

        String[] prevPaymentValues = this.currentPaymentDetails.values().toArray(new String[0]);
        String strCondition = genTableColNames[0] + " = '" + prevPaymentValues[0] + "' AND " + genTableColNames[1] +
                " = '" + prevPaymentValues[1] +"' ";

        querySQL = BuilderSQL.templateUPDATE(generalCatTable, genTableColNames,strCondition);
        DBParam[] param = Utils.addDBParams(data);
        boolean res = DBController.getInstance().setToDB(querySQL,param);

    }

    public boolean comparePayments(Object[] obj) {

        String[] PKColumns = getPKColumns();
        ArrayList<Object> checkObj = new ArrayList<>();
        String condition1 ="";

        for(int i = 0; i < PKColumns.length; i++){
            for(int k = 0; k < genTableColNames.length; k++){
                if(PKColumns[i].equals(genTableColNames[k])){
                    condition1 += genTableColNames[k] + " = ? AND ";
                    checkObj.add(obj[k]);
                }
            }
        }
        condition1 = condition1.substring(0, condition1.length() -4 );
        querySQL = BuilderSQL.templateSELECT("*",
                generalCatTable, condition1, 50);
        DBParam[] param = Utils.addDBParams(checkObj.toArray());
        DBResult res1 = DBController.getInstance().getFromDB(querySQL, param);

        return res1 == null;
    }

    public boolean compareEditPaymentPKValues (Object[] prevValues, Object[] newValues){
        String[] PKColumns = Controller.getInstance().getPKColumns();
        ArrayList<Boolean> compares = new ArrayList<>();

        for(int i = 0; i < PKColumns.length; i++){
            for(int k = 0; k < genTableColNames.length; k++){
                if(PKColumns[i].equals(genTableColNames[k])){
                    String prevValue = prevValues[k].toString();
                    String newValue = newValues[k].toString();
                    compares.add(prevValue.equals(newValue));
                }
            }
        }
        for(Boolean comp : compares){
            if(!comp)
                return false;
        }

        return true;
    }



    public String[] getPKColumns() {
        String condition = "table_name = '" + generalCatTable + "' AND constraint_name = '" + generalCatTable + "_pkey'";
        querySQL = BuilderSQL.templateSELECT("column_name","INFORMATION_SCHEMA.KEY_COLUMN_USAGE",
                condition,50);
        DBResult res = DBController.getInstance().getFromDB(querySQL, null);
        Object[] PKColumns = res.getValues("column_name");
        return Arrays.copyOf(PKColumns, PKColumns.length, String[].class);
    }

    public String[] getFriendlyColNames(String tableName) {
        querySQL = BuilderSQL.templateSELECT("*", "friendly_names" ,
                "tablename = ?",  50);
        DBParam[] param = Utils.addDBParams(tableName);
        DBResult res = DBController.getInstance().getFromDB(querySQL, param);
        Map<String, String> row = res.getRow(0);
        String[] resFull = row.values().toArray(new String[0]);
        String[] resNames;
        for(int i = 1; i < resFull.length; i++){
            if(resFull[i].equals("")){
                resNames = Arrays.copyOfRange(resFull, 1, i);
                return resNames;
            }
        }
        resNames = Arrays.copyOfRange(resFull, 1, resFull.length -1);
        return resNames;
    }


}
