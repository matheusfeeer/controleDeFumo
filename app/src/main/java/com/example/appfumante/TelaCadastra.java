package com.example.appfumante;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TelaCadastra extends AppCompatActivity {
    EditText nome, cpf, cidade, email, senha, confirmaSenha;
    String strNome, strCpf, strCidade, strEmail, strSenha, strConfirmaSenha;
    public static int vape, pod, narguile, cigarro, cigarroEletronico, charuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastra);
        getSupportActionBar().hide();
        nome = findViewById(R.id.cadastroCampoNome);
        cpf = findViewById(R.id.cadastroCampoCpf);
        cidade = findViewById(R.id.cadastroCampoCidade);
        email = findViewById(R.id.cadastroCampoEmail);
        senha = findViewById(R.id.cadastroCampoSenha);
        confirmaSenha = findViewById(R.id.cadastroConfirmaSenha);
    }
    public void verificacoesCadastro(View v){
        strNome = nome.getText().toString();
        strCpf = cpf.getText().toString();
        strCidade = cidade.getText().toString();
        strEmail = email.getText().toString();
        strSenha = senha.getText().toString();
        strConfirmaSenha = confirmaSenha.getText().toString();
        vape = 0;
        pod = 0;
        narguile = 0;
        cigarro = 0;
        cigarroEletronico = 0;
        charuto = 0;

        if(!TextUtils.isEmpty(strNome) || !TextUtils.isEmpty(strCpf) // Verifica se está tudo preenchido
                || !TextUtils.isEmpty(strCidade) || !TextUtils.isEmpty(strEmail)
                || !TextUtils.isEmpty(strSenha) || !TextUtils.isEmpty(strConfirmaSenha)){
            if (strSenha.equals(strConfirmaSenha)) { // Verifica se a confirmação de senha está correta
                if(strCpf.length() == 11){ // Verifica se o tamanho do CPF está certo

                    // Daqui pra baixo verifica se o usuário já existe
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Usuários");
                    reference.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot){
                            for (DataSnapshot d : snapshot.getChildren()) {
                                if (d.getValue(Usuarios.class).getCpf().equals(strCpf)){
                                   toast("Usuário já cadastrado.");
                                   break;
                                } else{
                                    Usuarios x = new Usuarios(strNome, strCpf, strCidade, strEmail, strSenha, strConfirmaSenha,
                                            vape, pod, narguile, cigarro, cigarroEletronico, charuto);
                                    x.salvar();
                                    nome.setText(null);
                                    cpf.setText(null);
                                    cidade.setText(null);
                                    email.setText(null);
                                    senha.setText(null);
                                    confirmaSenha.setText(null);
                                    voltarSemView();
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error){}
                    }); // Acaba aqui

                } else{
                    toast("Preencha apenas os 11 dígitos do seu CPF.");
                    cpf.setText(null);
                }
            } else{
                toast("Confirmação de senha incorreta.");
                senha.setText(null);
                confirmaSenha.setText(null);
            }
        } else{
            toast("Preencha todos os campos.");
        }
    }
    public void esconderTeclado(View editText) { // esconde o teclado ao clicar fora do cpf
        InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public void voltarSemView(){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
    public void toast(String msg){ // método criado porque o override no método enviarEmail buga o toast
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    public void voltar(View v){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}