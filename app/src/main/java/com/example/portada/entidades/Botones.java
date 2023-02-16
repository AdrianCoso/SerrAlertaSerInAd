package com.example.portada.entidades;

import android.content.ContentValues;

public class Botones {

    private int id_boton;
    private int numero;
    private String texto;
    private int color;
    private String imagen;
    private String audio;
    private String activado;

    public Botones(int numero, String texto, int color, String imagen, String audio, String activado) {
        this.numero = numero;
        this.texto = texto;
        this.color = color;
        this.imagen = imagen;
        this.audio = audio;
        this.activado = activado;
    }

    public Botones(){

    }

    public int getId_boton() {
        return id_boton;
    }

    public void setId_boton(int id_boton) {
        this.id_boton = id_boton;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getActivado() {
        return activado;
    }

    public void setActivado(String activado) {
        this.activado = activado;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();

        values.put("numero", numero);
        values.put("texto", texto);
        values.put("color", color);
        values.put("imagen", imagen);
        values.put("activado", activado);

        return values;
    }
}
