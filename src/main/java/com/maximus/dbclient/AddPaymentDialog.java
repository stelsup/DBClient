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

    private String[] columnNames;
    private String[] columnTypes;
    private Object[] addData;



    @Override
    public void onShow() {

        this.columnNames = Controller.getInstance().getGenTableColNames();
        this.columnTypes = Controller.getInstance().getGenTableColTypes();

        dataFields = new Control[columnNames.length];


        TextField firstField = new TextField();
        firstField.setText(Controller.getInstance().getCurrentObjectName());
        firstField.setEditable(false);


        ColumnConstraints col1 = new ColumnConstraints(200,200,200);
        ColumnConstraints col2 = new ColumnConstraints(280,280,280);
        RowConstraints row = new RowConstraints(20,60,60);
        addGridPane.getColumnConstraints().add(0,col1);
        addGridPane.getColumnConstraints().add(1,col2);

        for(int i = 0; i < columnNames.length; i++){

            Label label = new Label(columnNames[i] + " :");

            addGridPane.getRowConstraints().add(i,row);
            addGridPane.add(label,0, i);
            addGridPane.setHalignment(label, HPos.CENTER);

            generateField(i);

            if( i == 0){
                addGridPane.add(firstField, 1, i);
            }else {
                addGridPane.add(dataFields[i], 1, i);
             }

        }

        //addGridPane.setGridLinesVisible(true);

    }


    public void generateField (int index){

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

    public void actionCancel(){
        super.closeWindow();
    }

    public void actionOk(){

        Object[] data = new Object[dataFields.length];

        for(int i = 0; i < dataFields.length; i++){
            if(dataFields[i] instanceof TextField) {
                TextField texf = (TextField) dataFields[i];
                if (texf.getTextFormatter().getValue() instanceof Double) {
                    Double doub = (Double) texf.getTextFormatter().getValue();
                    data[i] = doub;
                }else if (texf.getTextFormatter().getValue() instanceof String) {
                    String str = (String) texf.getTextFormatter().getValue();
                    data[i] = str;
                }
            }else if(dataFields[i] instanceof DatePicker) {
                LocalDate date = ((DatePicker) dataFields[i]).getValue();
                data[i] = date;
            }else if(dataFields[i] instanceof Spinner) {
                data[i] = ((Spinner<?>) dataFields[i]).getValueFactory().getValue();
            }
        }

        this.addData = data;
    }


    public Object[] getAddData () {return this.addData;}


}
