package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OtrasConfiguraciones extends AppCompatActivity {

    SharedPreferences preferencias;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_configuraciones);

        //Inicializar los objetos

        preferencias = getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE);

        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);

        Button btnObtener = (Button) findViewById(R.id.btnObtener);

        EditText ptDuracion = (EditText) findViewById(R.id.ptDuracion);

        ptDuracion.setText(preferencias.getInt(Preferencias.DURACION,0)); //He puesto 0 por poner un número

        EditText ptNumeroBotones = (EditText) findViewById(R.id.ptNumeroBotones);

        ptNumeroBotones.setText(preferencias.getInt(Preferencias.NUMEROBOTONES,0));

        EditText ptConexionBluetooth = (EditText) findViewById(R.id.ptConexionBluetooth);

        ptConexionBluetooth.setText(preferencias.getString(Preferencias.CONEXIONBLUETOOTH,""));

        EditText ptTipoAlerta = (EditText) findViewById(R.id.ptTipoAlerta);

        ptTipoAlerta.setText(preferencias.getString(Preferencias.TIPOALERTA,""));

        //Cuando pulsas el botón Guardar

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                preferencias = getSharedPreferences(Preferencias.DATOS, Context.MODE_PRIVATE);
                editor = preferencias.edit();

                //Tiempo de duración

               // editor.putInt(Preferencias.DURACION, ptDuracion.getText().);

                //Número de botones

                //Conexión Bluetooth

                editor.putString(Preferencias.CONEXIONBLUETOOTH, ptConexionBluetooth.getText().toString());

                //Tipo de alarma

                editor.putString(Preferencias.TIPOALERTA, ptTipoAlerta.getText().toString());
            }
        });




        //Cuando pulsas el botón Obtener

    }
}