package com.example.moicaroline.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Producto_BD.class}, version = 5)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ProductoDao productoDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bolsa_moicaroline")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

