package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SelectorImagenes extends AppCompatActivity {

    GridView listadoImagenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector_imagenes);

        listadoImagenes = findViewById(R.id.lvImagenes);

        Integer[] imagenes = new Integer[]{R.drawable.imagen1_nueva, R.drawable.imagen2_nueva, R.drawable.imagen3_nueva, R.drawable.imagen4_nueva};

        listadoImagenes.setAdapter(new AdaptadorImagenes(getBaseContext(), imagenes));
        listadoImagenes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int seleccionado = imagenes[position];
                Intent intent = new Intent();
                intent.putExtra("idImagen", seleccionado);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }
}