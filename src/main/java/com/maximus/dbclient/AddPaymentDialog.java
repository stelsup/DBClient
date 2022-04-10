package com.maximus.dbclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

public class AddPaymentDialog  extends GUIController {

    @FXML
    private GridPane addGridPane;
    @FXML
    private Control[] dataFields;
    @FXML
    private Button addButton;
    @FXML
    private Button addCancel;

    private final int iMaxStringFieldLen = 30;

    private String objectName;
    private String[] columnNames;
    private String[] friendlyColumnNames;
    private String[] columnTypes;
    private Object[] addData;

    private static boolean editMode = false;
    private String[] changeData;


    @Override
    public void onShow() {

        this.columnNames = Controller.getInstance().getGenTableColNames();
        this.friendlyColumnNames = Controller.getInstance().getFriendlyTableNames();
        this.columnTypes = Controller.getInstance().getGenTableColTypes();
        this.objectName = Controller.getInstance().getCurrentObjectName();

        dataFields = new Control[columnNames.length];


        TextField firstField = new TextField();
        firstField.setText(objectName);
        firstField.setEditable(false);
        firstField.setDisable(true); // New!


        ColumnConstraints col1 = new ColumnConstraints(200,200,200);
        ColumnConstraints col2 = new ColumnConstraints(280,280,280);
        RowConstraints row = new RowConstraints(20,60,60);
        addGridPane.getColumnConstraints().add(0,col1);
        addGridPane.getColumnConstraints().add(1,col2);

        for(int i = 0; i < columnNames.length; i++){

            Label label = new Label(friendlyColumnNames[i] + " :");

            addGridPane.getRowConstraints().add(i,row);
            addGridPane.add(label,0, i);
            addGridPane.setHalignment(label, HPos.CENTER);

            if(editMode){
                this.changeData = Controller.getInstance().getCurrentPaymentDetails().values().toArray(new String[0]);
                generateEditField(i);
            }else {
                generateNewField(i);
            }

            if( i == 0){
                addGridPane.add(firstField, 1, i);
            }else {
                addGridPane.add(dataFields[i], 1, i);
             }

        }

    }


    public void generateNewField (int index){

        TextFormatter<Double> INT;
        UnaryOperator<TextFormatter.Change> filter;


        switch(columnTypes[index]) {
            case "numeric" :
                dataFields[index] = new TextField();
                filter = change -> {
                    String str = change.getControlNewText();
                    if(str.matches("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$")) {
                        return change;
                    }
                    return null;
                };
                INT = new TextFormatter<Double>(filter);//можно задать значение по умолчанию
                TextField text = (TextField) dataFields[index];
                text.setTextFormatter(INT);
                text.setPromptText("Введите число");
                break;
            case "date" :
                dataFields[index] = new DatePicker();
                break;
            case "boolean" :
                ObservableList<String> confirm = FXCollections.<String>observableArrayList("TRUE","FALSE");
                dataFields[index] = new Spinner<String>(confirm);
                Spinner<String> spinS = (Spinner<String>)dataFields[index];
                spinS.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                break;
            case "smallint" :
                dataFields[index] = new Spinner<Integer>(1,12,1);
                Spinner<Integer> spin = (Spinner<Integer>)dataFields[index];
                spin.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                break;
            default:
                dataFields[index] = new TextField();
                TextField textField = (TextField) dataFields[index];
                textField.setPromptText("Введите данные");
        }

    }

    public void generateEditField (int index){

        TextFormatter<Double> INT;
        UnaryOperator<TextFormatter.Change> filter;


        switch(columnTypes[index]) {
            case "numeric" :
                dataFields[index] = new TextField();
                filter = change -> {
                    String str = change.getControlNewText();
                    if(str.matches("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$")) {
                        return change;
                    }
                    return null;
                };
                INT = new TextFormatter<Double>(filter);//можно задать значение по умолчанию
                TextField text = (TextField) dataFields[index];
                text.setTextFormatter(INT);
                text.setPromptText("Введите число");
                text.setText(changeData[index]);
                break;
            case "date" :
                dataFields[index] = new DatePicker();
                DatePicker datePick = (DatePicker) dataFields[index];
                LocalDate dt = LocalDate.parse(changeData[index]);
                datePick.setValue(dt);
                break;
            case "boolean", "bool" :
                ObservableList<String> confirm = FXCollections.<String>observableArrayList("TRUE","FALSE");
                dataFields[index] = new Spinner<String>(confirm);
                Spinner<String> spinS = (Spinner<String>)dataFields[index];
                spinS.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                //spinS.getValueFactory().setValue(changeData[index]);
                break;
            case "smallint" :
                dataFields[index] = new Spinner<Integer>(1,12,1);
                Spinner<Integer> spin = (Spinner<Integer>)dataFields[index];
                spin.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
                spin.getValueFactory().setValue(Integer.parseInt(changeData[index]));
                break;
            default:
                dataFields[index] = new TextField();
                TextField textField = (TextField) dataFields[index];
                textField.setPromptText("Введите данные");
                textField.setText(changeData[index]);
        }

    }

    public void actionCancel(){
        this.addData = null;
        super.closeWindow();
    }

    public void actionOk(){

        Object[] data = new Object[dataFields.length];

        data[0] = this.objectName;

        for(int i = 1; i < dataFields.length; i++){
            if(dataFields[i] instanceof TextField) {
                TextField texf = (TextField) dataFields[i];
                if (texf.getTextFormatter() != null) {
                    try {
                        if(Utils.parseFloatPoint(texf.getText())){
                            Double doub = Double.parseDouble(texf.getText());
                            data[i] = doub;
                        }else if (!texf.getText().equalsIgnoreCase("")){
                            Integer intg = Integer.parseInt(texf.getText());
                            data[i] = intg;
                        }else {
                            data[i] = null;
                        }
                    } catch (NumberFormatException e) {
                        numFormCheckAddData(i);
                        data[i] = null;
                        e.printStackTrace();
                        return;
                    }
                }else {
                    String str = texf.getText();
                    if(str.length() > iMaxStringFieldLen) {
                        textFormCheckAddData(i);
                        data[i] = null;
                        return;
                    } else {
                        data[i] = str;
                    }
                }
            }else if(dataFields[i] instanceof DatePicker) {
                DatePicker datPick = (DatePicker) dataFields[i];
                datPick.getEditor().commitValue();
                LocalDate date = datPick.getValue();
                data[i] = date;
            }else if(dataFields[i] instanceof Spinner) {
                data[i] = ((Spinner<?>) dataFields[i]).getValueFactory().getValue();
                if (data[i] instanceof String)
                    data[i] = Boolean.parseBoolean(String.valueOf(data[i]));
            }
        }

        this.addData = data;
        if(notNULLCheckAddData())
            super.closeWindow();
    }

    public static void setEditMode(boolean value){
        editMode = value;
    }





    public Object[] getAddData () {return this.addData;}

    public boolean notNULLCheckAddData () {


        for(int i = 0; i < addData.length; i++){
            if(addData[i] == null){
                ButtonType result = Utils.MessageBox( "Внимание", "Некоторые поля не заполнены!","Заполните поле: " + friendlyColumnNames[i],
                        Alert.AlertType.WARNING, null);
                return false;
            }
        }
        return true;
    }

    public void numFormCheckAddData (int indexField) {

        ButtonType result = Utils.MessageBox( "Внимание", "Переполнение значения!","Некорректное значение в поле: " + friendlyColumnNames[indexField],
                Alert.AlertType.WARNING, null);

    }

    public void textFormCheckAddData (int indexField) {
        ButtonType result = Utils.MessageBox( "Внимание", "Переполнение значения!","Введите значение не более "
                        + String.valueOf(iMaxStringFieldLen) + " символов в поле: " + friendlyColumnNames[indexField],
                Alert.AlertType.WARNING, null);
    }
}
