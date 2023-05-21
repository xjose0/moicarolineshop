package com.example.moicaroline.ui.categoriaview;

public class Producto {
    private String nombre;
    private String descripcion;
    private String precio;
    private String imagen;
    private String imagen_geometral;
    private String categoria;

    public Producto() {
        // Constructor vacío necesario para Firebase
    }

    public Producto(String nombre, String descripcion, String precio, String imagen, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.categoria = categoria;
    }

    public Producto(String nombre, String precio, String imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
    }

    // Getters y setters

    public String getImagen_geometral() {
        return imagen_geometral;
    }

    public void setImagen_geometral(String imagen_geometral) {
        this.imagen_geometral = imagen_geometral;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCategoria() {
        return categoria;
    }
}

