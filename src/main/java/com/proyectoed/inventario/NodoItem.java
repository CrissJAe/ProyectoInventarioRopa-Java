package com.proyectoed.inventario;

public class NodoItem {
    // Data de item
    public String nombre;
    public int precio;
    public String talla;
    public String marca;
    public String material;
    public String color;
    public String tipoPrenda;
    public byte estilo; 
    
    public NodoItem siguiente;

    //Constructor
    public NodoItem(String nombre, int precio, String marca, String talla, String material, String color, String tipoPrenda, byte estilo) {
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
        this.talla = talla;
        this.material = material;
        this.color = color;
        this.tipoPrenda = tipoPrenda;
        this.estilo = estilo;
        
        this.siguiente = null;
    }
}
