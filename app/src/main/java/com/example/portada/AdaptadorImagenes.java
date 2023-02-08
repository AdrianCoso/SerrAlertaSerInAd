package com.example.portada;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class AdaptadorImagenes extends ArrayAdapter {
    private Integer[] imagenes;
    public AdaptadorImagenes(Context context, Integer[]imagenes){
        super(context, R.layout.elemento_selector_imagen, imagenes);
        this.imagenes = imagenes;

    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        LayoutInflater mostrado = LayoutInflater.from(getContext());

        View elementoMostrado = mostrado.inflate(R.layout.elemento_selector_imagen, parent, false);
        ImageView vistaImagen = (ImageView) elementoMostrado.findViewById(R.id.imageView);
        vistaImagen.setImageResource(imagenes[position]);

        return elementoMostrado;
    }

}
