package com.maximus.dbclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AboutController extends GUIController {

    @FXML
    Button buttonOk;
    @FXML
    ImageView generalImage;
    @FXML
    Label nameLabel;

    Stage thisStage;

    @Override
    public void onShow() {
        thisStage = (Stage) super.scene.getWindow();
        thisStage.initStyle(StageStyle.UNDECORATED);
        generalImage.setImage(new Image("file://" + Utils.getImagesPath() + "general_secyr96.png"));
        nameLabel.setFont(Font.font("Bookman Old Style", FontWeight.BOLD, 17.0));
    }

    public void onCloseAbout() {
        super.closeWindow();
    }
}
