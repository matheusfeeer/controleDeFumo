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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    public static String email;
    public static int cigarro;
    public static int cigarro_eletronico;
    TextView tipoFumo, nomeUsuario, valorTotal;
    FirebaseAuth auth;
    FirebaseUser user;
    ImageView iv;
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
        btAbaixar = findViewById(R.id.btVoltar);
        btAumentar = findViewById(R.id.btFrente);
        iv = findViewById(R.id.fotoCigarro);
        user = auth.getCurrentUser();
        aux = 1;
        btAbaixar.setVisibility(View.INVISIBLE); // deixa o botão de voltar invisível pq já esta na primeira tela
    }

    public void mudancaDeTela(){ // muda as telas de acordo com o tipo de cigarro
        switch(aux){ // o Switch tem como entrada a variável aux (que foi definida como 1 no onCreate)
            case 1: // sempre começa aqui ao iniciar o app
                tipoFumo.setText("CIGARRO");
                btAbaixar.setVisibility(View.INVISIBLE); // deixa o botão de voltar invisível pq já esta na primeira tela
                btAumentar.setVisibility(View.VISIBLE);
                nomeUsuario.setText(email);
                valorTotal.setText("Total: "+cigarro);
            break;
            case 2:
                tipoFumo.setText("CIGARRO ELETRÔNICO");
                btAbaixar.setVisibility(View.VISIBLE);
                btAumentar.setVisibility(View.INVISIBLE);
                valorTotal.setText("Total: "+cigarro_eletronico);
            break;
        }
    }

    public void adicionarRegistro(View adicionar){ // onclick do botão de adicionar 1 na contagem
        if (aux == 1){
            toast("+1 Cigarro");
            cigarro = cigarro + 1;
            mudancaDeTela();
            Usuarios user = new Usuarios(email, cigarro, cigarro_eletronico);
            user.salvar();
        } else if (aux == 2){
            toast("+1 Cigarro eletrônico");
            cigarro_eletronico = cigarro_eletronico + 1;
            mudancaDeTela();
            Usuarios user = new Usuarios(email, cigarro, cigarro_eletronico);
            user.salvar();
        } else{
            toast("ERROR");
        }
    }

    public void setaAumentar(View aumentar) throws IOException {
        if (aux <= 5){ // se não encaixar nesse if não vai acontecer nada
            aux++; // aumenta 1 pra variável aux (que começa em 1 no onCreate)
            mudancaDeTela(); // chama o método do switchCase e vai entrar em um case diferente do previamente definido no onCreate
        }
    }

    public void setaAbaixar(View abaixar) throws IOException {
        if (aux >= 2) { // se não encaixar nesse if não vai acontecer nada
            aux = aux - 1; // diminui 1 pra variável aux
            mudancaDeTela(); // chama o método do switchCase e vai entrar em um case diferente do previamente definido no onCreate
        }
    }

    public void sair(View v) {
        auth.signOut();
        finish();
        Intent screen = new Intent(this, MainActivity.class);
        startActivity(screen);
    }

    public void toast(String msg){ // método criado porque o override no método enviarEmail buga o toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}