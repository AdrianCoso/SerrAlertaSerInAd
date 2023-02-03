package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import com.example.portada.db.DbHelper;
import com.example.portada.db.dbBotones;
import com.example.portada.entidades.Botones;

import java.util.ArrayList;

public class MisBotones extends AppCompatActivity {
    ListView lista;
    ArrayList<Botones> alertas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_botones);

        /*dbBotones db = new dbBotones(getApplicationContext());

        alertas = db.mostrarBotones();*/

        Botones boton_comer = new Botones(1, "comer", 0x57730A, "comer.jpg", "activado");
        Botones boton_jugar = new Botones(2, "jugar", 0x2FA59F, "jugar.jpg", "desactivado");
        alertas.add(boton_comer);
        alertas.add(boton_jugar);
        lista = findViewById(R.id.lvLista);
        lista.setAdapter(new Adaptador(this, alertas));
    }
}