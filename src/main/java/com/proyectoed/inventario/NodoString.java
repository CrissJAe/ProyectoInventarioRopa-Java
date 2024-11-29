package com.proyectoed.inventario;

public class NodoString {
    public String data;
    public NodoString siguiente;
    
    public NodoString(String data) {
        this.data = data;
        this.siguiente = null;
    }
}