package com.example.appfumante;

import android.view.View;
import android.widget.TextView;

public class configFumos {
    static TextView tipoFumo;
    public static void cigarro(){ // 1
        String fumo = "CIGARRO";
        tipoFumo.setText(fumo);
    }
    public static void cigarroEletronico(){ // 2
        String fumo = "CIGARRO ELETRÔNICO";
        tipoFumo.setText(fumo);
    }
}