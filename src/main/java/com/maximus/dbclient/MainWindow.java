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

        composeObjectsList();
        // выберем какой-то там
        if(listviewObjects.getItems().size() > 0)
            listviewObjects.getSelectionModel().select(1); ///!!!!
//        composeCategoriesList();
//         composePaymentsTable();
    }

    public void onObjectSelected()
    {
        if(listviewObjects.getItems().size() > 0)
        {
            int selectedIdx = listviewObjects.getSelectionModel().getSelectedIndex();
            //try {
                //if (selectedIdx > 0)
                    Controller.getInstance().setCurrentObjectName(listviewObjects.getItems().get(selectedIdx).toString());
//            } catch() {
//
//            }
            composeCategoriesList();
        }
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

            String[] strText = res.values().toArray(new String[0]);

            listviewPaymentInfo.getItems().clear();;
            listviewPaymentInfo.getItems().add(strText);
        }
    }

    public void composeObjectsList()
    {
        ArrayList<ObjectItemInfo> objects = Controller.getInstance().getObjects();

        listviewObjects.setEditable(false);
        listviewObjects.getItems().clear();

        for(ObjectItemInfo obj: objects) {
            listviewObjects.getItems().add(obj.getName());
        }
    }

    public void composeCategoriesList()
    {
        ArrayList<CategoriesItemInfo> categories = Controller.getInstance().getCategories();
        ObservableList<CategoriesItemInfo> cats = FXCollections.<CategoriesItemInfo>observableArrayList(categories);
        listviewCategories.setItems(cats);

//        listviewCategories.getItems().clear();
//
//        for(CategoriesItemInfo category : categories) {
//            listviewCategories.getItems().add(category.getName());
//        }
    }

    public void composePaymentsTable()
    {
        ArrayList<PaymentsItemInfo> payments = Controller.getInstance().getPayments();
        ObservableList<PaymentsItemInfo> paymentsList = FXCollections.observableArrayList(payments);

        tableviewPayments.setEditable(false);
        tableviewPayments.getColumns().clear();

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


        /*
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            table.getColumns().add(tc);
        }
         */

//        for(PaymentsItemInfo payment: payments) {
//            tableviewPayments.getItems().add(payment);
//        }

    }

    /*
           StackPane root = new StackPane();
        String[][] staffArray = {{"nice to ", "have", "titles"},
                                 {"a", "b", "c"},
                                 {"d", "e", "f"}};
        ObservableList<String[]> data = FXCollections.observableArrayList();
        data.addAll(Arrays.asList(staffArray));
        data.remove(0);//remove titles from data
        TableView<String[]> table = new TableView<>();
        for (int i = 0; i < staffArray[0].length; i++) {
            TableColumn tc = new TableColumn(staffArray[0][i]);
            final int colNo = i;
            tc.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
                    return new SimpleStringProperty((p.getValue()[colNo]));
                }
            });
            tc.setPrefWidth(90);
            table.getColumns().add(tc);
        }
        table.setItems(data);
        root.getChildren().add(table);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
     */


}