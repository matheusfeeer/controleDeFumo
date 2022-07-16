package com.example.appfumante;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText email, senha;
    Button logar, esqueceuSuaSenha, cadastrar;
    CheckBox mostrarSenha;
    FirebaseAuth auth;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        email = findViewById(R.id.campoEmail);
        senha = findViewById(R.id.campoSenha);
        logar = findViewById(R.id.botaoLogin);
        esqueceuSuaSenha = findViewById(R.id.botaoRecuperar);
        cadastrar = findViewById(R.id.botaoCadastro);
        mostrarSenha = findViewById(R.id.cbMostrarSenha);
        auth = FirebaseAuth.getInstance();
        alert = new AlertDialog.Builder(this);
    }
    @Override
    protected void onStart(){
    // esse método checa se o usuário já está logado no celular. Caso esteja, vai direto pra tela dos fumos
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            mudaTelaFumo();
        }
    }
    public void logar(View l){
        String loginEmail = email.getText().toString();
        String loginSenha = senha.getText().toString();

        if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginSenha)){
            auth.signInWithEmailAndPassword(loginEmail, loginSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mudaTelaFumo();
                    } else{
                        String error = task.getException().getMessage();
                        alert.setTitle("ERRO DO CONSOLE:");
                        alert.setMessage(error);
                        alert.show();
                    }
                }
            });
        }
    }
    public void mudaTelaFumo() {
        Intent logar = new Intent(this, TelaFumo.class);
        startActivity(logar);
        finish();
    }
    public void esconderTeclado(View editText) { // esconde o teclado ao clicar fora do cpf
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public void mostrarSenha(View mS){
        // não funciona ainda
    }
    public void mudaTelaEsqueceuSuaSenha(View ESS){
        Intent esqueceuSuaSenha = new Intent(this, TelaRecuperar.class);
        startActivity(esqueceuSuaSenha);
        finish();
    }
    public void mudaTelaCadastro(View aP) {
        Intent cadastrar = new Intent(this, TelaCadastra.class);
        startActivity(cadastrar);
        finish();
    }
}