package com.example.appfumante;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Usuarios {
    String email;
    int cigarro, cigarroEletronico;

    public Usuarios(){
    }

    public Usuarios(String email, int cigarro, int cigarroEletronico){
        this.email = email;
        this.cigarro = cigarro;
        this.cigarroEletronico = cigarroEletronico;
    }
    public void salvar(){ // esse método faz a ligação com o banco de dados
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Usuários").child(email).setValue(this); // E-mail é a PK
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
}
