package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.maximus.dbclient.Utils.showWindow;

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
    private TextArea areaPaymentDetails;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField searchField;


    public void onShow() {

        //setWindowTitle("Система платежей");

        listviewObjects.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewCategories.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableviewPayments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listviewObjects.setEditable(false);
        listviewCategories.setEditable(false);
        tableviewPayments.setEditable(false);
        areaPaymentDetails.setEditable(false);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        searchField.setDisable(true);

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
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        addButton.setDisable(false);
        searchField.setDisable(false);

        Object obj = listviewCategories.getItems().get(listviewCategories.getSelectionModel().getSelectedIndex());
        CategoriesItemInfo item = (CategoriesItemInfo)obj;
        String view  = item.getView();
        String table = item.getTable();
        Controller.getInstance().setCurrentCategory(view);
        Controller.getInstance().setGeneralTableParams(table);

        composePaymentsTable();
    }

    public void onPaymentSelected()
    {
        editButton.setDisable(false);
        deleteButton.setDisable(false);

        ObservableList selectedItems = tableviewPayments.getSelectionModel().getSelectedItems();
        if(selectedItems.size() > 0) {
            PaymentsItemInfo payment = (PaymentsItemInfo)selectedItems.get(0);

            Map<String, String> res = Controller.getInstance().getPaymentDetails(payment);
            StringBuilder builder = Utils.buildTextArea(res);

            System.out.println(builder);
            areaPaymentDetails.setPrefRowCount(5);
            areaPaymentDetails.setText(builder.toString());

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

    public void addPayment() throws IOException {


        GUIWindow addPaymentWindow = showWindow("AddPaymentDialog.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),700,500);

        AddPaymentDialog paymentDialog = (AddPaymentDialog)addPaymentWindow.getController();
        Object[] addPayment = paymentDialog.getAddData();
        if(addPayment != null) {
            if(Controller.getInstance().comparePayments(addPayment[0],addPayment[1])){
                Controller.getInstance().addPayment(addPayment);
                composePaymentsTable();
            }else {
                ButtonType result = Utils.MessageBox( "Предупреждение", "Добавление невозможно!","Запись с такими данными уже существует.",
                        Alert.AlertType.WARNING, null);
            }

        }
    }

    public void deletePayment(){

        if(Controller.getInstance().getCurrentPayment() == null)
            return;

        ButtonType result = Utils.MessageBox( "Предупреждение", "Предупреждение","Вы уверены, что хотите удалить запись?",
                Alert.AlertType.CONFIRMATION, null);
        if(result == ButtonType.OK) {
            Controller.getInstance().deleteCurrentPayment();
            composePaymentsTable();
        }
    }

    public void editPayment() throws IOException {

        String[] prevPaymentValues = Controller.getInstance().getCurrentPaymentDetails().values().toArray(new String[0]);
        AddPaymentDialog.setEditMode(true);


        GUIWindow addPaymentWindow = showWindow("AddPaymentDialog.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),700,500);
        AddPaymentDialog paymentDialog = (AddPaymentDialog)addPaymentWindow.getController();
        Object[] addPayment = paymentDialog.getAddData();
        if(addPayment != null){
            if(Utils.compareEditPaymentPKValues(prevPaymentValues, addPayment)){
                Controller.getInstance().editCurrentPayment(addPayment);
                composePaymentsTable();
            }else if(Controller.getInstance().comparePayments(addPayment[0],addPayment[1])){
                Controller.getInstance().editCurrentPayment(addPayment);
                composePaymentsTable();
            }else{
                ButtonType result = Utils.MessageBox( "Предупреждение", "Изменение невозможно!","Запись с такими данными уже существует.",
                        Alert.AlertType.WARNING, null);
            }
        }
        AddPaymentDialog.setEditMode(false);
    }

    public void searchPayment(){

    }

}