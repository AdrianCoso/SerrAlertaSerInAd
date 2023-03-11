package com.example.portada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AlertaActivity extends AppCompatActivity {

    TextView tvTextoAlerta;
    ImageView ivImagenAlerta;
    Button btnVerBotones, btnCerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerta);

        // Inicializar vistas;
        tvTextoAlerta = (TextView) findViewById(R.id.tvTextoAlertaMostrada);
        ivImagenAlerta = (ImageView) findViewById(R.id.ivImagenAlertaMostrada);
        btnVerBotones = (Button) findViewById(R.id.btnVerBotones);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);

        // Obtenener los datos de la alerta del intent que nos trae a la actividad
        Intent intent = getIntent();
        String mensajeRecibido = intent.getStringExtra("mensaje");
        int colorRecibido = intent.getIntExtra("color", 0);
        String imagenRecibida = intent.getStringExtra("imagen");

        // Establecer aspecto de las vistas según los atributos del botón pulsado
        tvTextoAlerta.setText(mensajeRecibido);
        ivImagenAlerta.setImageURI(Uri.parse(imagenRecibida));
        getWindow().getDecorView().setBackgroundColor(colorRecibido);

        // Establecer comportamiento del botón para ver los botones declarados
        btnVerBotones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVerBotones = new Intent(getApplicationContext(), MisBotones.class);
                intentVerBotones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentVerBotones);
            }
        });

        // Establecer el comportamiento del botón para cerrar la alerta
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //tono.stop();
    }
}