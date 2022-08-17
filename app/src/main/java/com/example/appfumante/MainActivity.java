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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText email, senha;
    Button logar, esqueceuSuaSenha, cadastrar;
    FirebaseAuth auth;
    FirebaseUser user;
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
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        alert = new AlertDialog.Builder(this);
    }

    public void cadastraFirebase(){
        String loginEmail = email.getText().toString();
        String emailDatabase = loginEmail.replaceAll("\\p{Punct}", "");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuários");
        reference.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean encontrou = false;
                for (DataSnapshot d : snapshot.getChildren()) {
                    String email = d.getValue(Usuarios.class).getEmail();
                    int cigarro = d.getValue(Usuarios.class).getCigarro();
                    int cigarro_eletronico = d.getValue(Usuarios.class).getCigarroEletronico();
                    if (email.equals(emailDatabase)){
                        encontrou = true;
                        TelaFumo.email = email;
                        TelaFumo.cigarro = cigarro;
                        TelaFumo.cigarro_eletronico = cigarro_eletronico;
                        break;
                    }
                }
                if (encontrou == false){
                    Usuarios u = new Usuarios(emailDatabase, 0, 0);
                    u.salvar();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    public void logar(View l){
        String loginEmail = email.getText().toString();
        String loginSenha = senha.getText().toString();
        if(!TextUtils.isEmpty(loginEmail) || !TextUtils.isEmpty(loginSenha)){ // caso nenhum campo esteja vazio
            auth.signInWithEmailAndPassword(loginEmail, loginSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                // a linha acima capta o email e a senha que foram preenchidos e joga para o FirebaseAuth (autenticação)
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){ // se a tentativa de login der certo (bater com o que tem no FirebaseAuth)
                        boolean emailVerificado = user.isEmailVerified();
                        if (emailVerificado == true){
                            cadastraFirebase();
                            finish();
                            Intent logar = new Intent(MainActivity.this, TelaFumo.class);
                            startActivity(logar);
                            finish(); // isso aqui serve pra tela anterior não ficar na pilhagem e começar a travar o celular
                        } else {
                            toast("Verifique seu e-mail.");
                            user.sendEmailVerification();
                        }
                    } else{ // se der erro
                        String error = task.getException().getMessage(); // capta o erro
                        alert.setTitle("ERRO DO CONSOLE:"); // título do alerta
                        alert.setMessage(error); // mensagem do erro
                        alert.show(); // e mostra
                    }
                }
            });

        }
    }

    public void mudaTelaEsqueceuSuaSenha(View ESS){ // onclick para o botão de esqueceu sua senha
        Intent esqueceuSuaSenha = new Intent(this, TelaRecuperar.class);
        startActivity(esqueceuSuaSenha);
        finish(); // isso aqui serve pra tela anterior não ficar na pilhagem e começar a travar o celular
    }

    public void mudaTelaCadastro(View ap) { // onclick para o botão de cadastrar-se
        finish();
        Intent screen = new Intent(this, TelaCadastra.class);
        startActivity(screen);
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void esconderTeclado(View editText) { // esconde o teclado ao clicar fora do cpf
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
}