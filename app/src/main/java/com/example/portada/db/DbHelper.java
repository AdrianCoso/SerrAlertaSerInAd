package com.example.portada.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.portada.entidades.Botones;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SerrAlerta.db";
    public static  final String TABLE_BOTONES = "t_botones";
    private static DbHelper sInstance;

    public DbHelper(Context context, String databaseName, Object o, int databaseVersion) {
        super(context, databaseName, null, databaseVersion);
    }


    public static synchronized DbHelper getInstance(Context context)
    {
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext(),DATABASE_NAME,null,DATABASE_VERSION);
        }
        return sInstance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_BOTONES + "("+
                "id_boton INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "numero INTEGER NOT NULL,"+
                "texto TEXT NOT NULL,"+
                "color INTEGER NOT NULL,"+
                "imagen TEXT,"+
                "audio TEXT NOT NULL,"+
                "activado TEXT)");


    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE "+TABLE_BOTONES);
        onCreate(sqLiteDatabase);
    }


}
