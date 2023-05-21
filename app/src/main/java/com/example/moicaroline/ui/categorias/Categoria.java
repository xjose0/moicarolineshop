package com.example.moicaroline.ui.categorias;

public class Categoria {
    private String nombre;
    private String imagen;
    private String coleccion; // coleccion1, etc.

    public Categoria() {}

    public Categoria(String nombre, String imagen, String coleccion) {
        this.nombre = nombre;
        this.imagen = imagen;
        this.coleccion = coleccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getColeccion() {
        return coleccion;
    }

    public void setColeccion(String coleccion) {
        this.coleccion = coleccion;
    }
}

