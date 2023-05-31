package com.example.aularecycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaCadastro extends AppCompatActivity {
    static ArrayList listaProdutos;
    EditText nome, categoria, preco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_cadastro);
        getSupportActionBar().hide();
        nome = findViewById(R.id.novoNome);
        categoria = findViewById(R.id.novoCat);
        preco = findViewById(R.id.novoPreco);
    }
    public void cadastra(View v){
        if(validaCampos()){
            efetivaCadastro();
        }
        else{
            Toast.makeText(this, "Preencha todos os campos corretamente!", Toast.LENGTH_SHORT).show();
        }
    }

    
    public void efetivaCadastro(){
        String n = nome.getText().toString();
        String c = categoria.getText().toString();
        float p = Float.parseFloat(preco.getText().toString());
        Produto produto = new Produto(n, c, p);
        Verifica(produto);
    }
    public boolean validaCampos(){
        if(nome.getText().toString().isEmpty()||categoria.getText().toString().isEmpty()||preco.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    public void Verifica(Produto p){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Produtos").child(p.getNome()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(TelaCadastro.this, "ja existe menor", Toast.LENGTH_SHORT).show();
                }
                else {
                    p.salvar();
                    TelaCadastro.super.onBackPressed();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}