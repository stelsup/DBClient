package com.maximus.dbclient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
    private VBox vboxObjects;
    @FXML
    private ToolBar toolBarCategory;
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
    @FXML
    private TextField edtPageNum;
    @FXML
    private Button btnPageLeft;
    @FXML
    private Button btnPageRight;
    @FXML
    private Button searchBtn;
    @FXML
    private Button refreshBtn;


    private Stage thisStage;
    private PageArea currentPage;
    private ArrayList<ObjectItemInfo> objects;
    private ArrayList<CategoriesItemInfo> categories;
    private ArrayList<PaymentsItemInfo> payments;



    public void onShow() {

        addButton.setGraphic(Utils.loadDOWTollBarImage(1));
        addButton.setGraphicTextGap(5.0);
        deleteButton.setGraphic(Utils.loadDOWTollBarImage(2));
        deleteButton.setGraphicTextGap(5.0);
        editButton.setGraphic(Utils.loadDOWTollBarImage(3));
        editButton.setGraphicTextGap(5.0);
        btnPageLeft.setGraphic(Utils.loadUPTollBarImage(1));
        btnPageRight.setGraphic(Utils.loadUPTollBarImage(2));
        searchBtn.setGraphic(Utils.loadUPTollBarImage(3));
        refreshBtn.setGraphic(Utils.loadUPTollBarImage(4));
        tableviewPayments.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableviewPayments.setEditable(false);
        areaPaymentDetails.setEditable(false);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        searchField.setDisable(true);
        searchBtn.setDisable(true);
        btnPageLeft.setDisable(true);
        btnPageRight.setDisable(true);
        refreshBtn.setDisable(true);
        edtPageNum.setEditable(false);

        composeObjectsList();

        thisStage = (Stage) super.scene.getWindow();
        thisStage.setOnCloseRequest(windowEvent ->{
            ButtonType result = Utils.MessageBox( "Предупреждение", "Выход из программы","Вы действительно хотите выйти ?",
                    Alert.AlertType.CONFIRMATION, null);
            if(result == ButtonType.OK){
                super.closeWindow();
            }else{
                windowEvent.consume();
            }
        });

    }

    public void onObjectSelected(String currentObj)
    {
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        searchField.setDisable(true);
        searchBtn.setDisable(true);
        refreshBtn.setDisable(true);

        Controller.getInstance().setCurrentObjectName(currentObj);
        composeCategoriesList();

    }

    public void onCategorySelected(String currentCat)
    {
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        addButton.setDisable(false);
        searchField.setDisable(false);
        searchBtn.setDisable(false);
        refreshBtn.setDisable(false);

        String view = "";
        String table = "";
        for(CategoriesItemInfo cat :categories){
            if(cat.getName().equals(currentCat)){
                 view  = cat.getView();
                 table = cat.getTable();
            }
        }
        Controller.getInstance().setCurrentCategory(view);
        String[] friendlyView = Controller.getInstance().getFriendlyColNames(view);
        Controller.getInstance().setFriendlyViewNames(friendlyView);
        Controller.getInstance().setGeneralTableParams(table);
        String[] friendlyTable = Controller.getInstance().getFriendlyColNames(table);
        Controller.getInstance().setFriendlyTableNames(friendlyTable);

        currentPage = new PageArea();
        composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());

    }

    public void onPaymentSelected()
    {
        editButton.setDisable(false);
        deleteButton.setDisable(false);

        ObservableList selectedItems = tableviewPayments.getSelectionModel().getSelectedItems();
        if(selectedItems.size() > 0) {
            PaymentsItemInfo payment = (PaymentsItemInfo)selectedItems.get(0);

            Map<String, String> res = Controller.getInstance().getPaymentDetails(payment);
            String[] friendlyColNames = Controller.getInstance().getFriendlyTableNames();
            StringBuilder builder = Utils.buildTextArea(res, friendlyColNames);

            System.out.println(builder);
            areaPaymentDetails.setPrefRowCount(5);
            areaPaymentDetails.setText(builder.toString());
            //areaPaymentDetails.setFont(Font.font("Bookman Old Style"));
            //areaPaymentDetails.setFont(Font.font("Courier New"));
            areaPaymentDetails.setFont(Font.font("Consolas"));

        }
    }

    public void composeObjectsList()
    {
        objects = Controller.getInstance().getObjects();
        ArrayList<TitledPane> result = new ArrayList<TitledPane>();
        ImageView picture;
        Image image;
        VBox strDetails;

        if (objects == null){
            Label nullObject = new Label("Объекты не найдены");
            nullObject.setFont(Font.font("Bookman Old Style",14.0));
            vboxObjects.getChildren().add(nullObject);
        }


        for(ObjectItemInfo object : objects){

            strDetails = new VBox();
            strDetails.setPadding(new Insets(5.0));
            strDetails.setSpacing(5.0);
            Label type = new Label(object.getTypeView());
            type.setFont(Font.font("Bookman Old Style"));
            Label adress = new Label(object.getAdressView());
            adress.setFont(Font.font("Bookman Old Style"));
            strDetails.getChildren().addAll(type, adress);

            image = Utils.loadObjImage(object.getType());
            picture = new ImageView(image);
            picture.setFitWidth(25);
            picture.setFitHeight(25);

            TitledPane tp = new TitledPane();
            tp.setText(object.getName());
            tp.setGraphic(picture);
            tp.setContent(strDetails);
            tp.setCollapsible(true);
            tp.setAnimated(true);
            tp.setOnMouseClicked(event-> {
                onObjectSelected(tp.getText());
            });

            result.add(tp);
        }

        vboxObjects.getChildren().addAll(result);
    }

    public void composeCategoriesList()
    {
        toolBarCategory.getItems().clear();
        categories = Controller.getInstance().getCategories();

        if (categories == null){
            Label nullCat = new Label("Категории не найдены");
            nullCat.setFont(Font.font("Bookman Old Style",14.0));
            vboxObjects.getChildren().add(nullCat);
        }

        ArrayList<Button> result = new ArrayList<Button>();
        ImageView picture;
        Image image;

        for(CategoriesItemInfo cat : categories){

            image = Utils.loadCatImage(cat.getName());
            picture = new ImageView(image);
            picture.setFitWidth(40);
            picture.setFitHeight(40);

            Button bt = new Button(cat.getName());
            bt.setFont(Font.font("Bookman Old Style", FontWeight.SEMI_BOLD,14.0));
            bt.setUserData(cat);
            bt.setPrefSize(toolBarCategory.getPrefWidth(),80.0);
            bt.setGraphic(picture);
            bt.setContentDisplay(ContentDisplay.RIGHT);
            bt.setTextAlignment(TextAlignment.CENTER);
            bt.setAlignment(Pos.CENTER_RIGHT);
            bt.setGraphicTextGap((cat.getName().length() < 10) ? 30.0 :5.0);
            bt.setOnMouseClicked(event-> {
                onCategorySelected(bt.getText());
            });
            result.add(bt);

        }

        toolBarCategory.getItems().addAll(result);

    }

    public boolean composePaymentsTable(int offset, int limit)
    {
        payments = Controller.getInstance().getPayments(offset, limit);
        ObservableList<PaymentsItemInfo> paymentsList;

        tableviewPayments.getColumns().clear();
        updateNavigateBar();

        if (payments == null) return false;

        paymentsList = FXCollections.observableArrayList(payments);
        String[] colNames = Controller.getInstance().getFriendlyViewNames();
        for(int colIdx = 0; colIdx < colNames.length; colIdx++ ) {
        String columnName = colNames[colIdx];
            TableColumn<PaymentsItemInfo, String> column = new TableColumn<>(columnName);
            int idx = colIdx;
            column.setCellValueFactory( cellData ->
                    cellData.getValue().getProperty(idx)  );

            tableviewPayments.getColumns().add(column);
        }

        tableviewPayments.setItems(paymentsList);

        return true;
    }

    public void addPayment() throws IOException {


        GUIWindow addPaymentWindow = showWindow("AddPaymentDialog.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),700,500,"Добавление платежа");

        AddPaymentDialog paymentDialog = (AddPaymentDialog)addPaymentWindow.getController();
        Object[] addPayment = paymentDialog.getAddData();
        if(addPayment != null) {
            if(Controller.getInstance().comparePayments(addPayment)){
                Controller.getInstance().addPayment(addPayment);
                composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());   /////!!!!!!
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
            composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());  ////!!!!!
        }
        deleteButton.setDisable(true);
        editButton.setDisable(true);
    }

    public void editPayment() throws IOException {

        String[] prevPaymentValues = Controller.getInstance().getCurrentPaymentDetails().values().toArray(new String[0]);
        AddPaymentDialog.setEditMode(true);


        GUIWindow addPaymentWindow = showWindow("AddPaymentDialog.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),700,500,"Редактирование платежа");
        AddPaymentDialog paymentDialog = (AddPaymentDialog)addPaymentWindow.getController();
        Object[] addPayment = paymentDialog.getAddData();
        if(addPayment != null){
            if(Controller.getInstance().compareEditPaymentPKValues(prevPaymentValues, addPayment)){
                Controller.getInstance().editCurrentPayment(addPayment);
                composePaymentsTable(currentPage.getOffset(), currentPage.getLimit()); /////!!!!
            }else if(Controller.getInstance().comparePayments(addPayment)){
                Controller.getInstance().editCurrentPayment(addPayment);
                composePaymentsTable(currentPage.getOffset(), currentPage.getLimit()); ///!!!!!
            }else{
                ButtonType result = Utils.MessageBox( "Предупреждение", "Изменение невозможно!","Запись с такими данными уже существует.",
                        Alert.AlertType.WARNING, null);
            }
        }
        AddPaymentDialog.setEditMode(false);
    }

    public void searchPayment(){
        String strSearch = searchField.getText();

        ArrayList<PaymentsItemInfo> payments = Controller.getInstance().searchPayment(strSearch);
        if (payments == null) {
            Utils.MessageBox( "Поиск", "Не найдено!","По запросу '" + strSearch + "' ничего не найдено!",
                    Alert.AlertType.WARNING, null);
            return;
        }
        ObservableList<PaymentsItemInfo> paymentsList;

        tableviewPayments.getColumns().clear();


        paymentsList = FXCollections.observableArrayList(payments);
        String[] colNames = Controller.getInstance().getFriendlyViewNames();
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

    public void updateNavigateBar() {
        btnPageLeft.setDisable(currentPage.getOffset() == 0);
        if (payments == null){
            btnPageRight.setDisable(true);
        }else {
            btnPageRight.setDisable(payments.size() < 50);
        }

        edtPageNum.setText(String.valueOf(currentPage.getUserPageNum()));

    }

    public void onPageLeft() {
        int numPage = Integer.parseInt(edtPageNum.getText());
        numPage --;
        this.currentPage.setPage(numPage);
        composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());

    }

    public void onPageRight() {

        int numPage = Integer.parseInt(edtPageNum.getText());
        numPage ++;
        this.currentPage.setPage(numPage);
        composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());

    }

    public void onRefresh() {
        composePaymentsTable(currentPage.getOffset(), currentPage.getLimit());
    }

    public void onCloseProgram() {
        ButtonType result = Utils.MessageBox( "Предупреждение", "Выход из программы","Вы действительно хотите выйти ?",
                Alert.AlertType.CONFIRMATION, null);
        if(result == ButtonType.OK){
            super.closeWindow();
        }
    }

    public void onAbout() throws IOException {
        GUIWindow aboutWindow = showWindow("About.fxml",
                new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),450,500,"О программе ");
    }
}