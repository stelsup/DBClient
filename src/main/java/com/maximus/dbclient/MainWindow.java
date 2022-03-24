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
       // paneRoot.set

        listviewObjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewCategories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewObjects.setEditable(false);
        listviewCategories.setEditable(false);


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
                if (selectedIdx > 0)
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
        int i = 0;
        for (String columnName : Controller.getInstance().getColumnNames()) {
            TableColumn<PaymentsItemInfo, String> column = new TableColumn<>(columnName);
            //column.setCellValueFactory(new SimpleStringProperty(payments.get()));
            final Integer colNo = i;
            column.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PaymentsItemInfo, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<PaymentsItemInfo, String> p) {
                    return p.getValue().getProperty(colNo)   /*((PaymentsItemInfo)column.getUserData()).getValue(colNo)*/;
                }
            });

            tableviewPayments.getColumns().add(column);
            i++;
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