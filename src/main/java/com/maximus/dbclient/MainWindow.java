package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainWindow extends GUIController {

    @FXML
    private AnchorPane paneRoot;
    @FXML
    private ListView listviewObjects;
    @FXML
    private ListView listviewCategories;
    @FXML
    private TableView tableviewPayments;
    @FXML
    private ListView listviewPaymentInfo;
    @FXML
    private TextArea areaPaymentDetails;

    public void onShow() {
/*
    setWindowTitle("Система платежей - [" + userName + "]");
 */
        listviewObjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewCategories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableviewPayments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewObjects.setEditable(false);
        listviewCategories.setEditable(false);
        tableviewPayments.setEditable(false);
        areaPaymentDetails.setEditable(false);

        composeObjectsList();
        // выберем какой-то там
        if(listviewObjects.getItems().size() > 0)
            listviewObjects.getSelectionModel().select(0); ///!!!!
//        composeCategoriesList();
//         composePaymentsTable();
    }

    public void onObjectSelected()
    {
        int selectedIdx = listviewObjects.getSelectionModel().getSelectedIndex();

        Controller.getInstance().setCurrentObjectName(listviewObjects.getItems().get(selectedIdx).toString());

        composeCategoriesList();

    }

    public void onCategorySelected()
    {
        Object obj = listviewCategories.getItems().get(listviewCategories.getSelectionModel().getSelectedIndex());
        CategoriesItemInfo item = (CategoriesItemInfo)obj;
        String view  = item.getView();
        Controller.getInstance().setCurrentCategory(view);

        composePaymentsTable();
    }

    public void onPaymentSelected()
    {
        ObservableList selectedItems = tableviewPayments.getSelectionModel().getSelectedItems();
        if(selectedItems.size() > 0) {
            PaymentsItemInfo payment = (PaymentsItemInfo)selectedItems.get(0);

            Map<String, String> res = Controller.getInstance().getPaymentDetails(payment);
            StringBuilder builder = Utils.buildTextArea(res);

            /*String[] strText = res.values().toArray(new String[0]);

            StringBuilder builder = new StringBuilder();
            for(String s : strText) {
                builder.append(s);
                builder.append(" ");
            }

            */

            areaPaymentDetails.setText(builder.toString());
           // listviewPaymentInfo.getItems().clear();;
           // listviewPaymentInfo.getItems().add(builder.toString());
        }
    }

    public void composeObjectsList()
    {
        listviewObjects.setDisable(false);
        listviewObjects.getItems().clear();

        ArrayList<ObjectItemInfo> objects = Controller.getInstance().getObjects();
        ObservableList<ObjectItemInfo> objs;
        if (objects == null) {
            listviewObjects.getItems().clear();
            listviewObjects.getItems().add("Объекты не найдены");
            listviewObjects.setDisable(true);
        }else {
            objs = FXCollections.<ObjectItemInfo>observableArrayList(objects);
            listviewObjects.setItems(objs);
        }
    }

    public void composeCategoriesList()
    {
        listviewCategories.setDisable(false);

        ArrayList<CategoriesItemInfo> categories = Controller.getInstance().getCategories();
        ObservableList<CategoriesItemInfo> cats;
        if (categories == null) {
            listviewCategories.getItems().clear();
            listviewCategories.getItems().add("Категории не найдены");
            listviewCategories.setDisable(true);
        } else {
            cats = FXCollections.<CategoriesItemInfo>observableArrayList(categories);
            listviewCategories.setItems(cats);
        }
    }

    public void composePaymentsTable()
    {
        ArrayList<PaymentsItemInfo> payments = Controller.getInstance().getPayments();
        ObservableList<PaymentsItemInfo> paymentsList;

        tableviewPayments.getColumns().clear();

        if (payments == null) return;

        paymentsList = FXCollections.observableArrayList(payments);
        String[] colNames = Controller.getInstance().getColumnNames();
        for(int colIdx = 0; colIdx < colNames.length; colIdx++ ) {
        String columnName = colNames[colIdx];
            TableColumn<PaymentsItemInfo, String> column = new TableColumn<>(columnName);
            int idx = colIdx;
            column.setCellValueFactory( cellData ->
                    cellData.getValue().getProperty(idx)  );

            tableviewPayments.getColumns().add(column);
        }

        tableviewPayments.setItems(paymentsList);

    }



}