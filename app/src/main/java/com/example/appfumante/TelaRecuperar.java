package com.example.appfumante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaRecuperar extends AppCompatActivity {
    Button telaRecuperar;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    EditText etEmail;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_recuperar);
        getSupportActionBar().hide();
        etEmail = findViewById(R.id.textoEmail);
    }
    public void volta(View r){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
        finish();
    }
    public void toast(String msg){ // método criado porque o override no método enviarEmail buga o toast
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    public void enviarEmail(View v) {
        String email = etEmail.getText().toString();
        if (!TextUtils.isEmpty(email)) {
            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                toast("E-mail enviado.");
                            } else{
                                String error = task.getException().getMessage();
                                alert.setTitle("ERRO DO CONSOLE:");
                                alert.setMessage(error);
                                alert.show();
                            }
                        }
                    });
        } else{
            toast("Por favor, insira um e-mail");
        }
    }
}