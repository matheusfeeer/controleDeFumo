package com.example.appfumante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class TelaCadastra extends AppCompatActivity {
    FirebaseAuth auth;
    EditText email, senha, confirmarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastra);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailCadastro);
        senha = findViewById(R.id.senhaCadastro);
        confirmarSenha = findViewById(R.id.confirmarSenhaCadastro);
    }
    public void enviar(View v){
        String strEmail = email.getText().toString();
        String strSenha = senha.getText().toString();
        String strConfirmarSenha = confirmarSenha.getText().toString();
        if (!TextUtils.isEmpty(strEmail) && !TextUtils.isEmpty(strSenha) && !TextUtils.isEmpty(strConfirmarSenha)){
            if (strSenha.equals(strConfirmarSenha)) {
                auth.createUserWithEmailAndPassword(strEmail, strSenha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            voltarSemView();
                        } else {
                            String error = task.getException().getMessage();
                            toast(error);
                        }
                    }
                });
            } else {
                senha.setText(null);
                confirmarSenha.setText(null);
                toast("As senhas digitadas não coincidem!");
            }
        }
        else{
            toast("Preencha todos os campos!");
        }
    }

    public void esconderTeclado(View editText) { // esconde o teclado ao clicar fora do cpf
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public void voltar(View v){
        finish();
        Intent screen = new Intent(this, MainActivity.class);
        startActivity(screen);
    }

    public void voltarSemView(){ // esse método só serve pra puxar ali no emailVerificacao pq o outro nao puxa por conta do View
        finish();
        Intent screen = new Intent(this, MainActivity.class);
        startActivity(screen);
    }

    public void toast(String msg){ // método criado porque o override no método enviarEmail buga o toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}