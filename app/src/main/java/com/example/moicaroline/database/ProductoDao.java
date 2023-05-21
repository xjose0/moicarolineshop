package com.example.moicaroline.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductoDao {
    @Insert
    void insert(Producto_BD producto);

    @Update
    void update(Producto_BD producto);

    @Delete
    void delete(Producto_BD producto);

    @Query("SELECT * FROM productos WHERE userId = :userId")
    LiveData<List<Producto_BD>> getAllProductos(String userId);
}

