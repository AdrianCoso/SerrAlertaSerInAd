package com.example.portada;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class ConfigBtActivity extends AppCompatActivity {

    Spinner spDispositivos;
    TextView tvNombreDispositivo;
    Button btnConectar, btnDesconectar;
    private String direccionMAC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_bt);

        // Declarar vistas
        spDispositivos = (Spinner) findViewById(R.id.spDispositivos);
        tvNombreDispositivo = (TextView) findViewById(R.id.tvNombreDispositivo);
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnDesconectar = (Button) findViewById(R.id.btnDesconectar);

        BluetoothAdapter adaptadorBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
        }
        Set<BluetoothDevice> dispositivosEmparejados = adaptadorBluetooth.getBondedDevices();
        ArrayList<String> nombresDispositivosEmparejados = new ArrayList<>();
        for (BluetoothDevice dispositivo :
                dispositivosEmparejados) {
            nombresDispositivosEmparejados.add(dispositivo.getAddress());
        }

        ArrayAdapter adaptadorSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nombresDispositivosEmparejados);
        spDispositivos.setAdapter(adaptadorSpinner);

        spDispositivos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                direccionMAC = nombresDispositivosEmparejados.get(position);
                BluetoothDevice dispositivoConectado = adaptadorBluetooth.getRemoteDevice(direccionMAC);
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions

                }
                tvNombreDispositivo.setText(dispositivoConectado.getName());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentServicioBt = new Intent(getApplicationContext(), BtService.class);
                intentServicioBt.putExtra("direccionMAC", direccionMAC);

                startService(intentServicioBt);
            }
        });

        btnDesconectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDesconectarBt = new Intent(getApplicationContext(), BtService.class);
                stopService(intentDesconectarBt);
            }
        });
    }
}