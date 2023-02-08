package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MisBotones extends AppCompatActivity {
    ListView lista;
    ArrayList<Botones> alertas;
    Button fabAddBoton;


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
            //Toast.makeText(MisBotones.this, boton.getTexto()+" creado", Toast.LENGTH_LONG).show();
        }

        lista = findViewById(R.id.lvLista);
        lista.setAdapter(new Adaptador(this, alertas));
        lista.deferNotifyDataSetChanged();

        //Añadir funcionalidad al botón para agregar botones
        fabAddBoton = findViewById(R.id.btnAddBoton);
        fabAddBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent crearBoton = new Intent(MisBotones.this, EditarCrearBoton.class);
                //formBotones.putExtra("idBoton", -1);
                startActivity(crearBoton);
            }
        });
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Botones item = (Botones) lista.getItemAtPosition(position);
                int idBoton = item.getId_boton();
                Intent editarBoton = new Intent(getBaseContext(), EditarCrearBoton.class);
                editarBoton.putExtra("idBoton", idBoton);
                getBaseContext().startActivity(editarBoton);
            }
        });

    }
}