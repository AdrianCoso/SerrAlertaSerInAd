package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class OtrasConfiguraciones extends AppCompatActivity {

    SharedPreferences preferencias;

    SeekBar seekDuracion;

    EditText ptNumeroBotones, ptConexionBluetooth, ptTipoAlerta;

    Button btnGuardar, btnObtener, conectarBluetoothBtn, configurarNotificacionesBtn;

    TextView tvDuracion, tvBotonesDisponibles, tvConexionBluetooth, tvTipoAlerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otras_configuraciones);

        //Inicializar los objetos

        seekDuracion = (SeekBar) findViewById(R.id.seekDuracion);

        ptNumeroBotones = (EditText) findViewById(R.id.ptNumeroBotones);

        ptConexionBluetooth = (EditText) findViewById(R.id.ptConexionBluetooth);

        ptTipoAlerta = (EditText) findViewById(R.id.ptTipoAlerta);

        btnGuardar = (Button) findViewById(R.id.btnGuardar);

        btnObtener = (Button) findViewById(R.id.btnObtener);

        tvDuracion = (TextView)  findViewById(R.id.tvDuracion);

        tvBotonesDisponibles = (TextView) findViewById(R.id.ptNumeroBotones);

        tvConexionBluetooth = (TextView) findViewById(R.id.ptConexionBluetooth);

        tvTipoAlerta = (TextView) findViewById(R.id.ptTipoAlerta);
        conectarBluetoothBtn = (Button) findViewById(R.id.conectar_bluetooth_btn);
        configurarNotificacionesBtn = (Button) findViewById(R.id.notificacion_config_btn);

        preferencias = getSharedPreferences("DATOS", Context.MODE_PRIVATE);


        //Cuando pulsas el botón Guardar

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int numeroBotones = Integer.parseInt(ptNumeroBotones.getText().toString().trim());
                String conexionBluetooth = ptConexionBluetooth.getText().toString(); //NOMBRE DEL DISPOSITIVO
                String tipoAlerta = ptTipoAlerta.getText().toString();

                SharedPreferences.Editor editor = preferencias.edit();

                editor.putInt("NUMEROBOTONES", numeroBotones);
                editor.putString("CONEXIONBLUETOOTH", conexionBluetooth);
                editor.putString("TIPOALERTA", tipoAlerta);

                editor.apply();
            }
        });


        //Cuando pulsas el botón Obtener

        btnObtener.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                int numeroBotones = preferencias.getInt("NUMEROBOTONES", 0);
                String conexionBluetooth = preferencias.getString("CONEXIONBLUETOOTH", "");
                String tipoAlerta = preferencias.getString("TIPOALERTA", "");

                tvBotonesDisponibles.setText("Botones disponibles: " + numeroBotones);
                tvConexionBluetooth.setText("Dispositivo conectado: " + conexionBluetooth);
                tvTipoAlerta.setText("Tipo de alerta: " + tipoAlerta);

            }
        });

        tvDuracion.setText(" " + seekDuracion.getProgress()); //Pinta el textView para que se muestre el valor según se mueva el seekbar

        seekDuracion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //Indicar que se aumente el valor y se muestra en el seekbar

                tvDuracion.setText("" + i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        conectarBluetoothBtn.setOnClickListener(v -> {
            Intent conexionBt = new Intent(this, ConfigBtActivity.class);
            startActivity(conexionBt);

        });

        configurarNotificacionesBtn.setOnClickListener(v -> {
            Intent configNotifiaciones = new Intent();
            configNotifiaciones.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            configNotifiaciones.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            configNotifiaciones.putExtra("android.provider.extra.APP_PACKAGE", getPackageName());
            startActivity(configNotifiaciones);
        });
    }

    // Pulsar conectar bluetooth para elegir un dispositivo



}