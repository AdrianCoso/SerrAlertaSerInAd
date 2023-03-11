package com.example.portada;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class BtService extends Service {
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket socketBt = null;
    private HiloConexion hiloConexion;
    String TAG = "btservice";

    public BtService() {
    }

    @Override
    public void onCreate() {
        BluetoothAdapter adaptadorBt = BluetoothAdapter.getDefaultAdapter();
        comprobarEstadoBt(adaptadorBt);

        // Crear el dispositivo y establecer su dirección MAC
        String direccionMAC = getSharedPreferences("configuracion", Context.MODE_PRIVATE).getString("direccionMAC", "");
        BluetoothDevice dispositivo;
        if (!direccionMAC.equals("")) {
            dispositivo = adaptadorBt.getRemoteDevice(direccionMAC);
            Log.d("brservice", "arrancando dirección MAC" + direccionMAC);
        } else {
            Log.d("btservice", "Dirección MAC nula");
            return;
        }

        // Crear el socket
        try {
            socketBt = crearSocketBluetooth(dispositivo);
            Log.d("btservice", "socket creado");
        } catch (IOException e) {
            Log.d("btservice", "Fallo al crear el socket");
        }

        // Conectar al socket
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            //MainActivity.peticionPermisoBT.launch(Manifest.permission.BLUETOOTH_CONNECT);
        }
        try {
            socketBt.connect();
            if (socketBt.isConnected()) {
                Log.d("btservice", "Socket conectado");
                Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();
            } else return;
        } catch (IOException e) {
            Log.d("btservice", "No se pudo conectar al socket");
            throw new RuntimeException(e);
        }

        hiloConexion = new HiloConexion(socketBt);

        // Crear una notificación que abra la portada de la aplicación al tocarla
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        // crear la notificación con un canal propio
        Notification notification;
        CharSequence name = "Notificación de conexión";
        String description = "Informa de la conexión Bluetooth con la botonera";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel("mi_canal_id", name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
        notification = new Notification.Builder(this, "mi_canal_id")
                .setContentTitle("Escuchando el dispositivo "+dispositivo.getName())
                .setContentText("Pulsa para abrir la aplicación")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setOngoing(true)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null) {
//            direccionMAC = intent.getStringExtra("direccionMAC");
//        } else {
//        }


        hiloConexion.start();
        return START_STICKY;
    }

    private BluetoothSocket crearSocketBluetooth(BluetoothDevice dispositivo) throws IOException {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

        }
        return dispositivo.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    private void comprobarEstadoBt(BluetoothAdapter adaptadorBt) {
        if (adaptadorBt == null) {
            Toast.makeText(getBaseContext(), "El dispositivo no soporta bluetooth", Toast.LENGTH_SHORT).show();
        } else if (!adaptadorBt.isEnabled()) {
            Toast.makeText(getBaseContext(), "El dispositivo no está habilitado", Toast.LENGTH_SHORT).show();
            //TODO Solicitar al usuario que habilite el bluetooth


        } else {
            //Toast.makeText(getBaseContext(), "Bluetooth operativo", Toast.LENGTH_SHORT).show();
            Log.d("btservice", "bt operativo");
        }
    }

    private class HiloConexion extends Thread {
        private final InputStream input;

        public HiloConexion(BluetoothSocket socket) {
            InputStream tmpInput;

            try {
                tmpInput = socket.getInputStream();
                Log.d("btservice", "obteniendo flujo de entrada");
            } catch (IOException e) {
                Log.d("btservice", "no es posible obtener flujo de entrada");
                throw new RuntimeException(e);
            }
            input = tmpInput;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1];
            int numeroBytes;

            // Mantener el bucle para escuchar mensajes entrantes
            while (true) {
                try {
                    numeroBytes = input.read(buffer);
                    //Toast.makeText(BtService.this, "Mensaje recibido", Toast.LENGTH_SHORT).show();
                    String mensajeRecibido = new String(buffer, 0, numeroBytes);
                    Log.d("btservice", "mensaje recibido"+mensajeRecibido);


                    // Procesar el mensaje recibido para saber si debemos mostrar el intent
                    DbBotones db = new DbBotones(BtService.this);
                    Botones botonPulsado = db.obtenerBotonPorMensaje(mensajeRecibido);

                    if (botonPulsado != null) {
                        // Lanzar un hilo que gestione la alerta
                        HiloAlerta hiloAlerta = new HiloAlerta(getApplicationContext(), botonPulsado);
                        hiloAlerta.start();

                        //startActivity(alerta);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "No se pudo leer el flujo de entrada");
                    break;
                }
            }
        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}