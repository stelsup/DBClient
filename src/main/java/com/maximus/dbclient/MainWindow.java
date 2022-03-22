package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
        composeCategoriesList();
         composePaymentsTable();
    }

    public void composeObjectsList()
    {
        ArrayList<ObjectItemInfo> objects = Controller.getInstance().getObjects();

        listviewObjects.setEditable(false);

        for(ObjectItemInfo obj: objects) {
            listviewObjects.getItems().add(obj.getName());
        }


    }

    public void composeCategoriesList()
    {
        ArrayList<CategoriesItemInfo> categoties = Controller.getInstance().getCategories();

        listviewCategories.setEditable(false);

        for(CategoriesItemInfo category : categoties) {
            listviewCategories.getItems().add(category.getName());
        }
    }

    public void composePaymentsTable()
    {
        ArrayList<PaymentsItemInfo> payments = Controller.getInstance().getPayments();
        ObservableList<PaymentsItemInfo> people = FXCollections.observableArrayList(payments);

        tableviewPayments.setEditable(false);

        for (String columnName : Controller.getInstance().getColumnNames()) {
            TableColumn<PaymentsItemInfo, String> column = new TableColumn<>(columnName);
            //column.setCellValueFactory(new SimpleStringProperty(payments.get()));
            tableviewPayments.getColumns().add(column);
        }

    }


}