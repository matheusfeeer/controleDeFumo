package com.example.appfumante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelaFumo extends AppCompatActivity {
    TextView tipoFumo, nomeUsuario, valorTotal, localizacao, idUsuario;
    FirebaseAuth auth;
    ImageView iv;
    String vape, pod, narguile, cigarro, cigarroEletronico, charuto;
    int aux;
    Button btAbaixar, btAumentar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fumo);
        getSupportActionBar().hide();

        configFumos.tipoFumo = tipoFumo;
        auth = FirebaseAuth.getInstance();
        tipoFumo = findViewById(R.id.textFumo);
        nomeUsuario = findViewById(R.id.textNome);
        valorTotal = findViewById(R.id.textTotal);
        localizacao = findViewById(R.id.textLocal);
        idUsuario = findViewById(R.id.textId);
        btAbaixar = findViewById(R.id.btVoltar);
        btAumentar = findViewById(R.id.btFrente);
        iv = findViewById(R.id.fotoCigarro);
        vape = "VAPE";
        pod = "POD";
        narguile = "VAPE";
        cigarro = "VAPE";
        cigarroEletronico = "VAPE";
        charuto = "VAPE";
        aux = 1;
        try {
            mudancaDeTela();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void mudancaDeTela() throws IOException{
        switch(aux){
            case 1: // vape
                vape();
                btAbaixar.setVisibility(View.INVISIBLE);
                btAumentar.setVisibility(View.VISIBLE);

            break;
            case 2:
                pod();
                btAbaixar.setVisibility(View.VISIBLE);
            break;
            case 3:
                narguile();
            break;
            case 4:
                cigarro();
            break;
            case 5:
                cigarroEletronico();
                btAbaixar.setVisibility(View.VISIBLE);
                btAumentar.setVisibility(View.VISIBLE);
            break;
            case 6:
                charuto();
                btAumentar.setVisibility(View.INVISIBLE);
            break;
        }
    }
    public void adicionarRegistro(View adicionar){
        if (tipoFumo.getText() == vape){
            TelaCadastra.vape = TelaCadastra.vape+1;
        } else if (tipoFumo.getText() == pod){

        } else if (tipoFumo.getText() == narguile){

        } else if (tipoFumo.getText() == cigarro){

        } else if (tipoFumo.getText() == cigarroEletronico){

        } else{

        }
    }
    public void setaAumentar(View aumentar) throws IOException {
        if (aux <= 6) {
            aux++;
            mudancaDeTela();
        }
    }
    public void setaAbaixar(View abaixar) throws IOException {
        if (aux >= 2) {
            aux = aux - 1;
            mudancaDeTela();
        }
    }
    public void acessarPerfil(View aP){
        Intent perfil = new Intent(this, TelaPerfil.class);
        startActivity(perfil);
        finish();
    }
    public void deslogar(View v) {
        Intent main = new Intent(this, MainActivity.class);
        auth.signOut();
        startActivity(main);
    }
    public void vape(){ // 1
        tipoFumo.setText(vape);
    }
    public void pod(){ // 2
        tipoFumo.setText(pod);
    }
    public void narguile(){ // 3
        tipoFumo.setText(narguile);
    }
    public void cigarro(){ // 4
        tipoFumo.setText(cigarro);
    }
    public void cigarroEletronico(){ // 5
        tipoFumo.setText(cigarroEletronico);
    }
    public void charuto(){ // 6
        tipoFumo.setText(charuto);
    }
}