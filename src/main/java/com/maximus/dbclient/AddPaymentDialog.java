package com.maximus.dbclient;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class AddPaymentDialog  extends GUIController {

    @FXML
    private GridPane addGridPane;
    @FXML
    private TextField objectField;
    @FXML
    private Button addButton;
    @FXML
    private Button addCancel;

    private String[] columnNames;


    @Override
    public void onShow() {

        this.columnNames = Controller.getInstance().getColumnNames();

        //addGridPane = new GridPane();
        objectField = new TextField();
        objectField.setText(Controller.getInstance().getCurrentObjectName());
        objectField.setEditable(false);


        ColumnConstraints col1 = new ColumnConstraints(200,200,200);
        ColumnConstraints col2 = new ColumnConstraints(280,280,280);
        RowConstraints row = new RowConstraints(20,60,60);
        addGridPane.getColumnConstraints().add(0,col1);
        addGridPane.getColumnConstraints().add(1,col2);

        for(int i = 0; i < columnNames.length; i++){
            TextField field = new TextField();
            field.setPromptText("Введите данные");
            Label label = new Label(columnNames[i] + " :");

            addGridPane.getRowConstraints().add(i,row);
            addGridPane.add(label,0, i);
            addGridPane.setHalignment(label, HPos.CENTER);
            if( i == 0){
                addGridPane.add(objectField, 1, i);
            }else {
                addGridPane.add(field, 1, i);
            }
        }

        //addGridPane.setGridLinesVisible(true);

    }

    public void actionCancel(){
        super.closeWindow();
    }

}
