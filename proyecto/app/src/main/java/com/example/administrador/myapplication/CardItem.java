package com.example.administrador.myapplication;

public class CardItem {
    private int id;
    private String nombre;
    private  int cantidad;
    private double precio;
    private double total;

    public CardItem(int id, String nombre,int cantidad, double precio, double total) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad=cantidad;
        this.precio = precio;
        this.total=total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public  int getCantidad() {return   cantidad;}
    public  void setCantidad( int cantidad)  { this.cantidad=cantidad;}


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
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}

