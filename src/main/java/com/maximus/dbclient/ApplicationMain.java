package com.maximus.dbclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.postgresql.jdbc;

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
        DBParam[] user = new DBParam[1];
        user[0] = new  DBParam("Максимов Максим Николаевич");
        ResultSet res = controller.getFromDB("loadObjects.txt",user);

        String result = "";
        try {
            while(res.next())
            {
                result += res.getString(1);
                res.
                result += "\n";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        System.out.println(result);

        launch();
    }
}