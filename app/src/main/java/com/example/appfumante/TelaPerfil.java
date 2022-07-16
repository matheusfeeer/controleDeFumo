package com.example.appfumante;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TelaPerfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil);
    }

    public void voltar(View aP){
        Intent voltar = new Intent(this, TelaFumo.class);
        startActivity(voltar);
    }

}