package com.maximus.dbclient;

import com.maximus.dbclient.DB.DBController;
import com.maximus.dbclient.DB.DBResult;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.maximus.dbclient.Utils.showWindow;


public class ApplicationMain extends javafx.application.Application {
    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage stage) throws IOException {
        DBController dbController = DBController.getInstance();
        Controller controller = Controller.getInstance();


        GUIWindow wndLoginDialog = showWindow("LoginDialog.fxml",
                    new GUIParam(Modality.APPLICATION_MODAL, null, GUIParam.ShowType.SHOWTYPE_SHOWWAIT),250,350,"Авторизация пользователя");

        LoginDialog loginDialog = (LoginDialog)wndLoginDialog.getController();
        if(loginDialog.getBtnResult() == ButtonType.CANCEL)
            return;

        GUIWindow wndMain = showWindow("MainWindow.fxml",
                    new GUIParam(Modality.NONE, null, GUIParam.ShowType.SHOWTYPE_SHOWNORMAL),750,1062,"Система платежей [ "
                        + Controller.getInstance().getUserName() + " ]");

        MainWindow mainWindow = (MainWindow)wndMain.getController();



    }

    @Override
    public void stop() throws Exception {

    }

    public static void main(String[] args) {
//        DBController controller = new DBController();
//        ////test/////
//
//        DBParam[] obj = new DBParam[1];
//        obj[0] = new DBParam("Максимов Максим Николаевич");
//        DBResult res1 = controller.getFromDB("loadObjects.txt", obj);
//        printTest(res1);
//
//
//        DBParam[] categ = new DBParam[1];
//        categ[0] = new DBParam("ОЗАЛРЯ52уч");
//        DBResult res2 = controller.getFromDB("loadCategories.txt", categ);
//        printTest(res2);
//
//        DBParam[] payment = new DBParam[1];
//        payment[0] = new DBParam("ОЗАЛРЯ52");
//        DBResult res3 = controller.getFromDB("loadPayments.txt", payment,"select * from public.easy_nalog");
//        printTest(res3);
//
//
//        DBParam[] infoPay = new DBParam[2];
//        infoPay[0] = new DBParam("ОЗАЛРЯ52");
//        infoPay[1] = new DBParam( LocalDate.of(2020,02,10));
//        DBResult res4 = controller.getFromDB("loadInfoPayment.txt", infoPay, "select * from public.nalog");
//        printTest(res4);
//
//        DBParam[] addPay = new DBParam[13];
//        addPay[0] = new DBParam("ОЗАЛРЯ52");
//        addPay[1] = new DBParam(LocalDate.of(2021,02,13));
//        addPay[2] = new DBParam(LocalDate.of(2020,01,01));
//        addPay[3] = new DBParam(217350);
//        addPay[4] = new DBParam(1);
//        addPay[5] = new DBParam(0);
//        addPay[6] = new DBParam(0.3);
//        addPay[7] = new DBParam(12);
//        addPay[8] = new DBParam(0);
//        addPay[9] = new DBParam(0);
//        addPay[10] = new DBParam(503);
//        addPay[11] = new DBParam(503);
//        addPay[12] = new DBParam(true);
//        controller.setToDB("addPayment.txt", addPay);
//
//        controller.disconnect();

    ///////////////////////////////
       launch();
    }

    public static void printTest (DBResult res) {
        List<Map<String, String>> r = res.getAllResult();

        for(Map element : r){
            System.out.println(element);
        }

        System.out.println("**********END**********");
    }
}
/*
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("New Order");
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseParkingSlotScreen.fxml"));
		Parent root = (Parent)fxmlLoader.load();
		ChooseParkingSlotController controller = fxmlLoader.<ChooseParkingSlotController>getController();
		controller.setUserId(userId);
		window.setScene(new Scene(root, ScreenSizesConstants.MapScreenWidth, ScreenSizesConstants.MapScreenHeight));
		window.show();
 */