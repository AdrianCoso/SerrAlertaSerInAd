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

        dbBotones db = new dbBotones(MisBotones.this);

        alertas = db.mostrarBotones();


        lista = findViewById(R.id.lvLista);
        lista.setAdapter(new Adaptador(this, alertas));
    }
}