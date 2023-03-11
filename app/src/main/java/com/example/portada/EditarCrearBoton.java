package com.example.portada;

import static java.security.AccessController.getContext;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;


import java.util.Arrays;

import yuku.ambilwarna.AmbilWarnaDialog;

public class EditarCrearBoton extends AppCompatActivity {

    private DbBotones db;
    private int colorDefecto;
    private View vistaPreviaColor;
    private TextView nombreTono;
    private EditText etTextoAlerta;
    private Button btnSeleccionColor;
    private Button btnSeleccionAudio;
    private Button btnSeleccionPicto;
    private Spinner spinner;
    private ImageView vistaPreviaPictoGrama;
    private ActivityResultLauncher<Intent> lanzadorSelectorFotos;
    private ActivityResultLauncher<Intent> lanzadorSelectorTonos;
    private Button btnCancelar;
    private Button btnGuardar;
    private Button btnEliminar;

    //Boton para crear o editar
    Botones botonCreado;

    //Datos para guardar
    int idBoton;
    int botonSeleccionado;
    String textoAlerta;
    String rutaTonoSeleccionado;
    int colorSeleccionado;
    String rutaImagenSeleccionada;
    // int idImagenSeleccionada;
    Ringtone tonoSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_crear_boton);

        //Abrir la base de datos
        db = new DbBotones(EditarCrearBoton.this);

        //Declarar vistas
        spinner = findViewById(R.id.spinnerBotones); //Spinner selección boton

        etTextoAlerta = findViewById(R.id.editTextTextoAlerta); //EditText para editar el texto que se mostrará

        vistaPreviaColor = findViewById(R.id.vistaPreviaColor); //Cuadro donde veremos el color seleccionado

        btnSeleccionColor = findViewById(R.id.btnSeleccionColor); //Botón que nos llevará al selector de colores

        nombreTono = findViewById(R.id.tvNombreTono); // Texto donde veremos el nombre del tono seleccionado

        btnSeleccionPicto = findViewById(R.id.btnSeleccionPicto); // Botón que nos llevará a la selección de imágenes

        vistaPreviaPictoGrama = findViewById(R.id.vistaPreviaPicto); // Cuadro donde veremos una miniatura de la imagen seleccionada

        btnSeleccionAudio = findViewById(R.id.btnSeleccionAudio); // Botón que nos llevará a la selección de sonidos del teléfono

        btnGuardar = findViewById(R.id.btnGuardar); //Botón para guardar los datos y volver a la lista de botones

        btnEliminar = findViewById(R.id.btnEliminar); // Botón para eliminar los datos del botón seleccionado

        btnCancelar = findViewById(R.id.btnCancelar); // Botón volver al listado sin guardar nada

        // Recoger el id del botón si es que hemos llegado a través de un elemento del listado.
        idBoton = getIntent().getIntExtra("idBoton", -1);
        if (idBoton != -1){ // Si recogemos un id seleccionamos el botón correspondiente y visibilizamos el botón para eliminiarlo
            DbBotones db = new DbBotones(EditarCrearBoton.this);
            botonCreado = db.mostrarBoton(idBoton);

        } else { // Si no creamos un botón generico
            botonCreado = new Botones(1,
                    "Alerta",
                    Color.CYAN,
                    "",
                    RingtoneManager.getActualDefaultRingtoneUri(EditarCrearBoton.this, RingtoneManager.TYPE_ALARM).toString(),
                    "activado");
        }

        //Inicializar las variables que usaremos para guardar el botón
        botonSeleccionado = botonCreado.getNumero();
        textoAlerta = botonCreado.getTexto();
        colorSeleccionado = botonCreado.getColor();
        rutaImagenSeleccionada = botonCreado.getImagen();
        rutaTonoSeleccionado = botonCreado.getAudio();

        // Inicializar los controles;
        Integer[] botones = {1, 2, 3, 4}; // Inicializamos los valores del spinner
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, botones);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(Arrays.asList(botones).indexOf(botonCreado.getNumero()));
        etTextoAlerta.setText(botonCreado.getTexto());
        colorDefecto = botonCreado.getColor();
        vistaPreviaColor.setBackgroundColor(botonCreado.getColor());
        vistaPreviaPictoGrama.setImageURI(Uri.parse(rutaImagenSeleccionada));
        nombreTono.setText(RingtoneManager.getRingtone(EditarCrearBoton.this, Uri.parse(botonCreado.getAudio())).getTitle(EditarCrearBoton.this));
        if (idBoton == -1) { // Si el botón existe mostrar la opción de eliminarlo
            btnEliminar.setVisibility(View.INVISIBLE);
        } else {
            btnEliminar.setVisibility(View.VISIBLE);
        }

        //Crear eventos
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // Selección del número de botón
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO asignar botón seleccionado
                botonSeleccionado = botones[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Crear la seleccion del color de fondo
        btnSeleccionColor.setOnClickListener(v -> abrirSelectorColor());

        //Crear la selección del audio de alerta
        lanzadorSelectorTonos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            //if (Build.VERSION.SDK_INT >= 33) {
                                Uri uriTonoSeleccionado = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                                rutaTonoSeleccionado = uriTonoSeleccionado.toString();
                                String stringTono =  RingtoneManager.getRingtone(EditarCrearBoton.this, uriTonoSeleccionado).getTitle(EditarCrearBoton.this);
                                nombreTono.setText(stringTono);

                            //}

                        }
                    }
                }
        );
        btnSeleccionAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorArchivoAudio();
            }
        });

        //Crear la selección del pictograma
        lanzadorSelectorFotos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        //Realizamos la operación de cargar la imagen
                        Uri uriImagenSeleccionada = data.getData();
                        //idImagenSeleccionada = data.getIntExtra("idImagen", R.drawable.imagen1_nueva);
                        getContentResolver().takePersistableUriPermission(uriImagenSeleccionada, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        rutaImagenSeleccionada = uriImagenSeleccionada.toString();
                        vistaPreviaPictoGrama.setImageURI(uriImagenSeleccionada);

                    }
                }
        );
        btnSeleccionPicto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelectorImagen();

            }
        });


        //Crear funcionalidad para botón guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guardarBoton();
            }
        });

        //Crear funcionalidad para botón eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarBoton();
            }
        });

        // Crear funcionalidad para botón cancelar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarCrearBoton.this, MisBotones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void eliminarBoton() {
        boolean correcto = db.eliminarBoton(idBoton);
        if (correcto) {
            borrarCanalNotificacion(idBoton);
            Intent intent = new Intent(EditarCrearBoton.this, MisBotones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            Toast.makeText(EditarCrearBoton.this, "No se puede eliminar", Toast.LENGTH_LONG).show();
        }
    }

    private void borrarCanalNotificacion(long idBoton) {
        String idCanal = new StringBuilder().append("canal_alerta_").append(String.valueOf(idBoton)).toString();
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.deleteNotificationChannel(idCanal);
    }

    private void crearCanalNotificacion(long idBoton, String nombre) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = new StringBuilder().append("canal_alerta_").append(String.valueOf(idBoton)).toString();
            String descripcion = new StringBuilder().append("Notificaciones del botón ").append(nombre).toString();
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, nombre, importance);
            channel.setDescription(descripcion);
            channel.setSound(Uri.parse(rutaTonoSeleccionado), new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build());
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void guardarBoton() {

        long id = -1;
        if (idBoton == -1) {
            id = db.insertarBoton(botonSeleccionado, etTextoAlerta.getText().toString(), colorDefecto , rutaImagenSeleccionada, rutaTonoSeleccionado.toString());
            crearCanalNotificacion(id, etTextoAlerta.getText().toString());

        } else {
            id = db.editarBoton(idBoton, botonSeleccionado, etTextoAlerta.getText().toString(), colorDefecto, rutaImagenSeleccionada, rutaTonoSeleccionado.toString());
            borrarCanalNotificacion(idBoton);
            crearCanalNotificacion(idBoton, etTextoAlerta.getText().toString());
        }

        if(id > 0 ){
            Toast.makeText(EditarCrearBoton.this, "registro añadido", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarCrearBoton.this, MisBotones.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }
    }



    private void abrirSelectorImagen() {

        //Creamos un intent de tipo imagen
        Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.INTERNAL_CONTENT_URI);


        lanzadorSelectorFotos.launch(i);
    }

    private void abrirSelectorArchivoAudio() {
        final Uri tonoActual = RingtoneManager.getActualDefaultRingtoneUri(EditarCrearBoton.this, RingtoneManager.TYPE_ALARM);
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Seleccione un tono de alerta");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, tonoActual);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        lanzadorSelectorTonos.launch(intent);

    }

    private void abrirSelectorColor() {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, colorDefecto,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // Dejamos este método vacío y el selector se cierra automáticamente al tocar cancelar

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        // change the mDefaultColor to
                        // change the GFG text color as
                        // it is returned when the OK
                        // button is clicked from the
                        // color picker dialog
                        colorDefecto = color;

                        // now change the picked color
                        // preview box to mDefaultColor
                        vistaPreviaColor.setBackgroundColor(colorDefecto);
                    }
                });
        colorPickerDialogue.show();

    }
}