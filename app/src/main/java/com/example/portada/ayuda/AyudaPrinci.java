package com.example.portada.ayuda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.portada.MainActivity;
import com.example.portada.MisBotones;
import com.example.portada.OtrasConfiguraciones;
import com.example.portada.R;

public class AyudaPrinci extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda_principal);

        Button crearBotones = (Button)findViewById(R.id.btnCrearBoton);
        crearBotones.setOnClickListener(this);

        Button desactivarBotones = (Button)findViewById(R.id.btnDesactivarBoton);
        desactivarBotones.setOnClickListener(this);

        Button mostrarAlertas = (Button)findViewById(R.id.btnMostrarAlertas);
        mostrarAlertas.setOnClickListener(this);

        Button otrasConfiguraciones = (Button)findViewById(R.id.btnOtrasConfiguraciones);
        otrasConfiguraciones.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        procesarEvento(view.getId());
    }

    public void procesarEvento(int opc){
        switch(opc){
            case R.id.btnCrearBoton:  startActivity(new Intent(AyudaPrinci.this, AyudaUtilidadesBotones.class)); break;
            case R.id.btnDesactivarBoton: startActivity(new Intent(AyudaPrinci.this, AyudaActivacionBotones.class)); break;
            case R.id.btnMostrarAlertas: startActivity(new Intent(AyudaPrinci.this, AyudaMostrarAlerta.class)); break;
            case R.id.btnOtrasConfiguraciones: startActivity(new Intent(AyudaPrinci.this, AyudaOtrasConfiguraciones.class)); break;
        }
    }
}