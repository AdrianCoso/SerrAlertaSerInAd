package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;

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

        // Obtenemos el intent que creó la alerta. Vendrá del servicio bt y tendrá como extra el string recibido
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

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}