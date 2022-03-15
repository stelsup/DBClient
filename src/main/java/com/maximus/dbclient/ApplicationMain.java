package com.maximus.dbclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        DBParam[] user = new DBParam[2];
        user[0] = new  DBParam("easy_nalog");
        user[1] = new DBParam("ОЗАЛРЯ52");
        DBResult res = controller.getFromDB("loadPayments.txt",user);

        List<Map<String, String>> r = res.getAllResult();

        for(Map element : r){
            System.out.println(element);
            System.out.println("///////////////////////////");
        }

    ///////////////////////////////
      //  launch();
    }
}