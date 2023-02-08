package com.example.portada.db;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.portada.entidades.Botones;

import java.util.ArrayList;

public class DbBotones extends DbHelper{

    Context context;
    public DbBotones(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarBoton(int numero, String texto, int color, int imagen, String audio){
        long id= 0;
        try{
            DbHelper dbHelper =  new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("numero",numero);
            values.put("texto",texto);
            values.put("color",color);
            values.put("imagen",imagen);
            values.put("audio",audio);
            values.put("activado", "activado");

            id = db.insert(TABLE_BOTONES, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public boolean eliminarBoton(int id){
        boolean correcto = false;

        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM "+TABLE_BOTONES+ " WHERE id_boton = '"+id+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public ArrayList<Botones> mostrarBotones(){
        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Botones> listaBotones = new ArrayList<>();
        Botones boton = null;
        Cursor cursorBotones = null;

        cursorBotones = db.rawQuery("SELECT * FROM "+TABLE_BOTONES, null);

        if(cursorBotones.moveToFirst()){
            do{
                boton = new Botones();
                boton.setId_boton(cursorBotones.getInt(0));
                boton.setNumero(cursorBotones.getInt(1));
                boton.setTexto(cursorBotones.getString(2));
                boton.setColor(cursorBotones.getInt(3));
                boton.setImagen(cursorBotones.getInt(4));
                boton.setAudio(cursorBotones.getString(5));
                boton.setActivado(cursorBotones.getString(6));
                listaBotones.add(boton);
            }while(cursorBotones.moveToNext());
        }

        cursorBotones.close();

        return listaBotones;
    }

    public Botones mostrarBoton(int id){
        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Botones boton = new Botones();
        Cursor cursorBotones = null;
        cursorBotones = db.rawQuery("SELECT * FROM " + TABLE_BOTONES + " WHERE id_boton="+ id, null);
        if (cursorBotones.moveToFirst()){
            boton.setId_boton(cursorBotones.getInt(0));
            boton.setNumero(cursorBotones.getInt(1));
            boton.setTexto(cursorBotones.getString(2));
            boton.setColor(cursorBotones.getInt(3));
            boton.setImagen(cursorBotones.getInt(4));
            boton.setAudio(cursorBotones.getString(5));
            boton.setActivado(cursorBotones.getString(6));

        }
        cursorBotones.close();
        return boton;
    }

    public int editarBoton(int id, int numero, String texto, int color, int imagen, String audio){
        int correcto = -1;

        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE "+TABLE_BOTONES+" SET numero = '"+ numero+"', texto = '"+texto+"', color = '"+color+"', imagen = '"+imagen+"', audio = '"+audio+"' WHERE id_boton = "+id);
            correcto = 1;
        } catch (Exception ex){
            ex.toString();
            correcto = -1;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean switchBoton(int id, boolean activado){
        boolean correcto = false;

        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            if (activado) {
                db.execSQL("UPDATE "+ TABLE_BOTONES+" SET activado = 'activado' WHERE id_boton = "+id);
            } else {
                db.execSQL("UPDATE "+ TABLE_BOTONES+" SET activado = 'desactivado' WHERE id_boton = "+id);
            }
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

}
