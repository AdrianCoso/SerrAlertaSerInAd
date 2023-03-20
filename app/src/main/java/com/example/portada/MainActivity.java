package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.portada.ayuda.AyudaPrinci;
import com.example.portada.db.DbHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ViewFlipper v_flipper;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button botonMisBotones = (Button)findViewById(R.id.button);
       botonMisBotones.setOnClickListener(this);
       ImageView informacion = (ImageView) findViewById(R.id.btnInfo);
       informacion.setOnClickListener(this);
       Button botonOtrasConfiguraciones = (Button)findViewById(R.id.button_2);
       botonOtrasConfiguraciones.setOnClickListener(this);
       Button prueba = (Button)findViewById(R.id.button2);
       prueba.setOnClickListener(this);

       DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());
       SQLiteDatabase db = dbHelper.getWritableDatabase();

        int images[] = {R.drawable.imagen1_nueva, R.drawable.imagen2_nueva, R.drawable.imagen3_nueva, R.drawable.imagen4_nueva};

        v_flipper = findViewById(R.id.v_flipper);

        for(int image: images){
            flipperImages(image);
        }


    }

    public void flipperImages(int image){
       ImageView imageView = new ImageView(this);
       imageView.setBackgroundResource(image);
       v_flipper.addView(imageView);
       v_flipper.setFlipInterval(3000);
       v_flipper.setAutoStart(true);

       v_flipper.setInAnimation(this, android.R.anim.slide_in_left);
       // v_flipper.setInAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onClick(View view) {
        procesarEvento(view.getId());
    }

    public void procesarEvento(int opc){
       switch(opc){
           case R.id.button:  startActivity(new Intent(MainActivity.this,MisBotones.class)); break;
           case R.id.button_2: startActivity(new Intent(MainActivity.this,OtrasConfiguraciones.class)); break;
           //case R.id.button_2: startActivity(new Intent(MainActivity.this,ConfigBtActivity.class)); break;

           case R.id.btnInfo: startActivity(new Intent(MainActivity.this, AyudaPrinci.class));break;
           case R.id.button2:{

               Intent pruebaIntent = new Intent(MainActivity.this,AlertaActivity.class);
               pruebaIntent.putExtra("color", -16711681);
               pruebaIntent.putExtra("mensaje", "Alerta1");
               pruebaIntent.putExtra("imagen", "content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2Fkid.jpg");
               startActivity(pruebaIntent);break;
           }
       }
    }

}