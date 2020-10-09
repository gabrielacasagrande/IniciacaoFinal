package com.example.mybusguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AbrirTelaLogin(View view){ startActivity(new Intent(this,Home.class));}


    public void AbrirTelaCadastro(View view){ startActivity(new Intent(this,TelaCadastro.class));}
}



