package com.example.portada;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.portada.entidades.Botones;

import java.io.IOException;

public class HiloAlerta extends Thread {
    Context contexto;
    long idBoton;
    String textoAlerta;
    String imagen;
    int color;
    String audio;
    static int idAlerta = 101;

    public HiloAlerta(Context contexto, Botones botonPulsado) {
        this.contexto = contexto; 

        idBoton = botonPulsado.getId_boton();
        textoAlerta = botonPulsado.getTexto();
        imagen = botonPulsado.getImagen();
        color = botonPulsado.getColor();
        audio = botonPulsado.getAudio();
        HiloAlerta.idAlerta++;
    }

    @Override
    public void run() {
        Intent alerta = new Intent(contexto, AlertaActivity.class);
        alerta.putExtra("mensaje", textoAlerta).
                putExtra("imagen", imagen).
                putExtra("color", color);
        alerta.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(contexto, 0, alerta, PendingIntent.FLAG_UPDATE_CURRENT);

        //Mostrar notificación para ver la alerta cuando el teléfono está bloqueado
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(contexto.getContentResolver(), Uri.parse(imagen));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String CHANNEL_ID = new StringBuilder().append("canal_alerta_").append(idBoton).toString();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(contexto, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("SerrAlertas")
                .setContentText(textoAlerta)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat.from(contexto).notify(HiloAlerta.idAlerta, builder.build());
        //RingtoneManager.getRingtone(contexto, Uri.parse(audio)).play();
    }
}
