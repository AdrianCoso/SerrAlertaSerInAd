package com.example.portada;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.portada.db.DbBotones;
import com.example.portada.entidades.Botones;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Botones> datos;

    public Adaptador(Context contexto, ArrayList<Botones> datos) {
        this.contexto = contexto;
        this.datos = datos;

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return datos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.elemento_lista, null);
        TextView titulo = (TextView) vista.findViewById(R.id.tvTitulo);
        TextView boton = (TextView) vista.findViewById(R.id.tvBoton);
        ImageView imagen = (ImageView) vista.findViewById(R.id.ivImagen);
        SwitchMaterial cambio = (SwitchMaterial) vista.findViewById(R.id.sw);
        CardView color = (CardView) vista.findViewById(R.id.cardView);
        FloatingActionButton fabEditar = (FloatingActionButton) vista.findViewById(R.id.fabEditar);


        titulo.setText(datos.get(i).getTexto());
        boton.setText("Botón: "+datos.get(i).getNumero());
        String rutaImagen = datos.get(i).getImagen();
        imagen.setImageURI(Uri.parse(rutaImagen));

        color.setCardBackgroundColor(datos.get(i).getColor());
        cambio.setChecked(datos.get(i).getActivado().equals("activado"));
        cambio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DbBotones db = new DbBotones(parent.getContext());

                db.switchBoton(datos.get(i).getId_boton(), isChecked);
            }
        });

        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vista.getContext(), EditarCrearBoton.class);
                intent.putExtra("idBoton", datos.get(i).getId_boton());
                vista.getContext().startActivity(intent);
            }
        });

        return vista;
    }



}
