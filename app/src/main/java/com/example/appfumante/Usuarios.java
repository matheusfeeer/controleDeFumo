package com.example.appfumante;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuarios {
    String nome, cpf, cidade, email, senha, confirmaSenha;
    int vape, pod, narguile, cigarro, cigarroEletronico, charuto;

    public Usuarios(){
    }

    public Usuarios(String nome, String cpf, String cidade, String email, String senha, String confirmaSenha,
                    int vape, int pod, int narguile, int cigarro, int cigarroEletronico, int charuto){
        this.nome = nome;
        this.cpf = cpf;
        this.cidade = cidade;
        this.email = email;
        this.senha = senha;
        this.confirmaSenha = confirmaSenha;
        this.vape = vape;
        this.pod = pod;
        this.narguile = narguile;
        this.cigarro = cigarro;
        this.cigarroEletronico = cigarroEletronico;
        this.charuto = charuto;
    }
    public void salvar(){ // esse método faz a ligação com o banco de dados
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuários").child(cpf).setValue(this); // Cpf é a PK
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }
    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public int getVape() {
        return vape;
    }
    public void setVape(int vape) {
        this.vape = vape;
    }

    public int getPod() {
        return pod;
    }
    public void setPod(int pod) {
        this.pod = pod;
    }

    public int getNarguile() {
        return narguile;
    }
    public void setNarguile(int narguile) {
        this.narguile = narguile;
    }

    public int getCigarro() {
        return cigarro;
    }
    public void setCigarro(int cigarro) {
        this.cigarro = cigarro;
    }

    public int getCigarroEletronico() {
        return cigarroEletronico;
    }
    public void setCigarroEletronico(int cigarroEletronico) {
        this.cigarroEletronico = cigarroEletronico;
    }

    public int getCharuto() {
        return charuto;
    }
    public void setCharuto(int charuto) {
        this.charuto = charuto;
    }
}
