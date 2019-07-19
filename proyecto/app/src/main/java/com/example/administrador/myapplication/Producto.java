package com.example.administrador.myapplication;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String imagen;
    private int existencias;
    private int categoria_id;
    private  int cantidad;

    public Producto(int id, String nombre, double precio, String imagen, int existencias, int categoria_id,int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.existencias = existencias;
        this.categoria_id = categoria_id;
        this.cantidad=cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public  int getCantidad() {return   cantidad;}
    public  void setCantidad( int precio)  { this.cantidad=cantidad;}


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}

