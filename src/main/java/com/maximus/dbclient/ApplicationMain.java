package com.maximus.dbclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;



public class ApplicationMain extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("MainWindow.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("LoginDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load()/*, 320, 240*/);
        stage.setTitle("Start");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        DBController controller = new DBController();
        ////test/////

        DBParam[] obj = new DBParam[1];
        obj[0] = new DBParam("Максимов Максим Николаевич");
        DBResult res1 = controller.getFromDB("loadObjects.txt", obj);
        printTest(res1);


        DBParam[] categ = new DBParam[1];
        categ[0] = new DBParam("ОЗАЛРЯ52уч");
        DBResult res2 = controller.getFromDB("loadCategories.txt", categ);
        printTest(res2);

        DBParam[] payment = new DBParam[1];
        payment[0] = new DBParam("ОЗАЛРЯ52");
        DBResult res3 = controller.getFromDB("loadPayments.txt", payment,"select * from public.easy_nalog");
        printTest(res3);


        DBParam[] infoPay = new DBParam[2];
        infoPay[0] = new DBParam("ОЗАЛРЯ52");
        infoPay[1] = new DBParam( LocalDate.of(2020,02,10));
        DBResult res4 = controller.getFromDB("loadInfoPayment.txt", infoPay, "select * from public.nalog");
        printTest(res4);

        DBParam[] addPay = new DBParam[13];
        addPay[0] = new DBParam("ОЗАЛРЯ52");
        addPay[1] = new DBParam(LocalDate.of(2021,02,13));
        addPay[2] = new DBParam(LocalDate.of(2020,01,01));
        addPay[3] = new DBParam(217350);
        addPay[4] = new DBParam(1);
        addPay[5] = new DBParam(0);
        addPay[6] = new DBParam(0.3);
        addPay[7] = new DBParam(12);
        addPay[8] = new DBParam(0);
        addPay[9] = new DBParam(0);
        addPay[10] = new DBParam(503);
        addPay[11] = new DBParam(503);
        addPay[12] = new DBParam(true);
        controller.setToDB("addPayment.txt", addPay);

        controller.disconnect();

    ///////////////////////////////
      //  launch();
    }

    public static void printTest (DBResult res) {
        List<Map<String, String>> r = res.getAllResult();

        for(Map element : r){
            System.out.println(element);
        }

        System.out.println("**********END**********");
    }
}