package com.example.moicaroline.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CarritoRepository {
    private ProductoDao productoDao;
    private LiveData<List<Producto_BD>> productos;

    public CarritoRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        productoDao = database.productoDao();
    }

    public LiveData<List<Producto_BD>> getAllProductos(String userId) {
        productos = productoDao.getAllProductos(userId);
        return productos;
    }

    public void insertProducto(Producto_BD producto) {
        // Ejecuta la inserción en un hilo separado utilizando AsyncTask o Coroutines
        new InsertProductoAsyncTask(productoDao).execute(producto);
    }

    public void updateProducto(Producto_BD producto) {
        // Ejecuta la actualización en un hilo separado utilizando AsyncTask o Coroutines
        new UpdateProductoAsyncTask(productoDao).execute(producto);
    }

    public void deleteProducto(Producto_BD producto) {
        // Ejecuta la eliminación en un hilo separado utilizando AsyncTask o Coroutines
        new DeleteProductoAsyncTask(productoDao).execute(producto);
    }

    private static class InsertProductoAsyncTask extends AsyncTask<Producto_BD, Void, Void> {
        private ProductoDao productoDao;
        public InsertProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto_BD... productos) {
            productoDao.insert(productos[0]);
            return null;
        }
    }

    private static class UpdateProductoAsyncTask extends AsyncTask<Producto_BD, Void, Void> {
        private ProductoDao productoDao;

        public UpdateProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto_BD... productos) {
            productoDao.update(productos[0]);
            return null;
        }
    }

    private static class DeleteProductoAsyncTask extends AsyncTask<Producto_BD, Void, Void> {
        private ProductoDao productoDao;

        public DeleteProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto_BD... productos) {
            productoDao.delete(productos[0]);
            return null;
        }
    }
}


