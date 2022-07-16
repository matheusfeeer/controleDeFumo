package com.example.appfumante;

import android.view.View;
import android.widget.TextView;

public class configFumos {
    static TextView tipoFumo;
    public static void vape(){ // 1
        String fumo = "VAPE";
        tipoFumo.setText(fumo);
    }
    public static void pod(){ // 2
        String fumo = "POD";
        tipoFumo.setText(fumo);
    }
    public static void narguile(){ // 3
        String fumo = "NARGUILÉ";
        tipoFumo.setText(fumo);
    }
    public static void cigarro(){ // 4
        String fumo = "CIGARRO";
        tipoFumo.setText(fumo);
    }
    public static void cigarroEletronico(){ // 5
        String fumo = "CIGARRO ELETRÔNICO";
        tipoFumo.setText(fumo);
    }
    public static void charuto(){ // 6
        String fumo = "CHARUTO";
        tipoFumo.setText(fumo);
    }
}