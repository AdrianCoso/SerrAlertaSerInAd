package com.example.portada;

import android.net.Uri;

public class Alerta {
    private int boton_asignado;
    private String texto;
    private int color;
    private Uri imagen;
    //private Uri sonido;
    private boolean activado;

    public Alerta(int boton_asignado, String texto, int color, Uri imagen, boolean activado) {

        this.boton_asignado = boton_asignado;
        this.texto = texto;
        this.color = color;
        this.imagen = imagen;
        //this.sonido = sonido;
        this.activado = activado;
    }

    public int getBoton_asignado() {
        return boton_asignado;
    }

    public String getTexto() {
        return texto;
    }

    public int getColor() {
        return color;
    }

    public Uri getImagen() {
        return imagen;
    }



    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
}
