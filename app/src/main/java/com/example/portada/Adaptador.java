package com.example.portada;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.portada.entidades.Botones;

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
        ImageView imagen = (ImageView) vista.findViewById(R.id.ivImagen);
        Switch cambio = (Switch) vista.findViewById(R.id.sw);
        ConstraintLayout fondo = (ConstraintLayout) vista.findViewById(R.id.fondo);

        titulo.setText(datos.get(i).getTexto());
        //imagen.setImageURI(Uri.parse(datos.get(i).getImagen()));
        cambio.setChecked(datos.get(i).getActivado() == "activado");
        fondo.setBackgroundColor(datos.get(i).getColor());
        return vista;
    }

}
