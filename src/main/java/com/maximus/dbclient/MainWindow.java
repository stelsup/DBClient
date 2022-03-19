package com.maximus.dbclient;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.util.ArrayList;

public class MainWindow extends GUIController {
    @FXML
    private ListView listviewObjects;
    @FXML
    private ListView listviewCategories;
    @FXML
    private TableView tableviewPayments;
    @FXML
    private ListView listviewPaymentInfo;

    public void onShow() {
/*
    setWindowTitle("Система платежей - [" + userName + "]");
 */

        composeObjectsList();
    }

    public void composeObjectsList()
    {
        ArrayList<ObjectItemInfo> objects = Controller.getInstance().getObjects();

        listviewObjects.setEditable(false);

        for(ObjectItemInfo obj: objects) {
            listviewObjects.getItems().add(obj.getName());
        }



        /*
            foreach(elem: objects)
            {
                 Button but1 = new Button();

            }

         */

    }

    public void composeCategoriesList()
    {
        //
    }
}