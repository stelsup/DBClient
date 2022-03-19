package com.maximus.dbclient;

import javafx.stage.Modality;
import javafx.stage.Window;

public class GUIParam {
    public Modality modality;
    public Window ownerParent;
    public ShowType showType;

    public GUIParam(Modality m, Window w, ShowType st) {
        this.modality = m;
        this.ownerParent = w;
        this.showType = st;
    }

    public enum ShowType {
        SHOWTYPE_SHOWNORMAL,
        SHOWTYPE_SHOWWAIT
    }
}
