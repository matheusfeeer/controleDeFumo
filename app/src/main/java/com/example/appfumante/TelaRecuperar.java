package com.example.appfumante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TelaRecuperar extends AppCompatActivity {
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
        finish();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }

    public void voltaSemView(){
        finish();
        Intent screen = new Intent(this, MainActivity.class);
        startActivity(screen);
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
                                voltaSemView();

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
    public void toast(String msg){ // método criado porque o override no método enviarEmail buga o toast
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}