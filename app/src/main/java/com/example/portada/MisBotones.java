package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;

import java.util.ArrayList;

public class MisBotones extends AppCompatActivity {
    ListView lista;
    ArrayList<Botones> alertas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_botones);


        DbBotones db = new DbBotones(MisBotones.this);

        alertas = db.mostrarBotones();
        if(alertas.isEmpty()){
            Toast.makeText(MisBotones.this, "vacio", Toast.LENGTH_LONG).show();
        }

        for(Botones boton: alertas){
            Toast.makeText(MisBotones.this, "holaaaa", Toast.LENGTH_LONG).show();
        }

        lista = findViewById(R.id.lvLista);
        lista.setAdapter(new Adaptador(this, alertas));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent formbotones = new Intent(view.getContext(), EditarCrearBoton.class);

                startActivity(formbotones);
            }
        });

    }
}