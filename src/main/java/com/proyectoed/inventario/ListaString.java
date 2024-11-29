package com.proyectoed.inventario;

// Clase que representa una lista enlazada de Strings
public class ListaString {
    public NodoString primero;
    public byte size;
    
    // Constructor
    public ListaString() {
        this.primero = null;
        this.size = 0;
    }
    
    // Verifica si la lista esta vacia
    public boolean estaVacio() {
        return primero == null;
    }
    
    // Agrega un elemento al comienzo de la lista
    public void agregar(String data) {
        NodoString nuevo = new NodoString(data);
        size++;

        nuevo.siguiente = primero;
        primero = nuevo;
    }
    
    // Elimina un elemento que coincide con String data
    public void eliminar(String data) {        
        NodoString actual = primero;
        NodoString anterior = null;
        
        // Busca el String en la lista
        while(actual != null) {
            if(actual.data.equalsIgnoreCase(data)) {
                // Se encontro el String

                if(actual.data.equals(primero.data)) {
                    // Coincide con el primero, por lo tanto actualiza el primero con el siguiente
                    primero = primero.siguiente;
                } else {
                    // No coincide con el primero, por lo tanto el anterior apunta al siguiente
                    // del actual, "saltandose" el actual.
                    anterior.siguiente = actual.siguiente;
                }
                size--;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }
    
    // Verifica si el String data ya existe dentro de la lista
    public boolean contiene(String data) {
        if(estaVacio())
            return false;

        NodoString actual = primero;
        
        // Busca el elemento en la lista
        while(actual != null) {
            if(data.equalsIgnoreCase(actual.data)) {
                // Se encontro un elemento que coincide, por lo tanto devuelve true
                // y termina la busqueda.
                return true;
            }
            
            actual = actual.siguiente;
        }
        
        // Devuelve false si no se encontro
        return false;
    }
    
    // Muestra los elementos de la lista. 
    // Si modoLista = true, los muestra en el formato:
    //  1. Elemento A
    //  2. Elemento B
    // y asi sucesivamente. (usos: true en tipos de prenda, false en historial)
    public void mostrar(boolean modoLista) {
        if(estaVacio()) {
            System.out.println("La lista esta vacia.");
            return;
        }
        
        NodoString actual = primero;
        byte i = 1;
        
        while(actual != null) {
            if(modoLista) System.out.println(" " + i + ". " + actual.data);
            else System.out.println(actual.data);
            i++;
            
            actual = actual.siguiente;
        }
    }
    
    // Calcula el tamanio de la lista en bytes
    public short calcularTamanio() {
        if (estaVacio()) {
            return 21; // Overhead + ReferenciaLista + byte size
        }
        
        // Inicia con el tamanio del byte size, el overhead y la referencia de la lista
        short tamanio = 21;
        
        // Recorre la lista sumando el tamanio de cada String
        NodoString actual = primero;
        while(actual != null) {
            // Suma el tamanio del String segun la formula
            // T = Overhead + ReferenciaString + CantidadChar * 2 + ReferenciaSiguiente
            tamanio += (short) actual.data.length() * 2 + 28;
            actual = actual.siguiente;
        }
        
        return tamanio;
    }
}
