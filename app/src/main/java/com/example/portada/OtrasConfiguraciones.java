package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtrasConfiguraciones extends AppCompatActivity {

    SharedPreferences preferencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_configuraciones);

        //Inicializar el objeto preferencias

        preferencias = getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE);

    }
}