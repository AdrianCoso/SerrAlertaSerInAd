package com.example.portada.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;

import com.example.portada.entidades.Botones;

import java.util.ArrayList;

public class dbBotones extends DbHelper{

    Context context;
    public dbBotones(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarBoton(int numero, String texto, int color, String imagen, String audio){
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
            values.put("activado", "True");

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
            db.execSQL("DELETE FROM "+TABLE_BOTONES+ " WHERE id = '"+id+"'");
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
                boton.setImagen(cursorBotones.getString(4));
                boton.setAudio(cursorBotones.getString(5));
                boton.setActivado(cursorBotones.getString(6));
            }while(cursorBotones.moveToNext());
        }

        cursorBotones.close();

        return listaBotones;
    }

    public boolean editarBoton(int id, int numero, String texto, int color, String imagen, String audio){
        boolean correcto = false;

        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE "+TABLE_BOTONES+" SET numero = '"+ numero+"', texto = '"+texto+"', color = '"+color+"', imagen = '"+imagen+"', audio = '"+audio+"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }

    public boolean desactivarBoton(int id){
        boolean correcto = false;

        DbHelper dbHelper =  new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE "+ TABLE_BOTONES+" SET activado = 'False' WHERE id = "+id);
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
