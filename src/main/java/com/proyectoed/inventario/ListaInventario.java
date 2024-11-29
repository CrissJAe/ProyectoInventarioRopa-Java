package com.proyectoed.inventario;

// Importa las funciones generarHistorial() y pausa() desde Main.java
import static com.proyectoed.inventario.Main.generarHistorial;
import static com.proyectoed.inventario.Main.pausa;

// Clase que representa una lista enlazada de productos.
public class ListaInventario {
    public NodoItem primero;

    // Cantidad de elementos en el inventario
    public byte size;
    
    // Constructor
    public ListaInventario() {
        this.primero = null;
        this.size = 0;
    }
    
    // Verifica si la lista esta vacia
    public boolean estaVacio() {
        return primero == null;
    }
    
    // Metodo para agregar un producto al inicio de la lista desde el teclado
    public void agregarProducto() {
        // Cada NodoItem puede llegar a pesar 513 bytes como maximo, segun sus atributos.
        // El tamanio de la lista se almacena en un short cuyo limite es 32767 (representa los bytes).
        // 32767 / 513 = 63.8, por lo tanto, cerramos el numero en 60 como limite.
        if(size == 60) {
            System.out.println("El inventario esta lleno.");
            return;
        }

        System.out.print("Ingrese el nombre de la prenda: ");
        String nombre = Validador.ingresarNombre(this);
        // Nota: this hace referencia a la instancia de la lista.

        System.out.print("Ingrese el precio de la prenda: ");
        int precio = Validador.ingresarInt(1, 1000000000);
        
        System.out.print("Ingrese la marca de la prenda: ");
        String marca = Validador.ingresarString(true);

        System.out.print("Ingrese la talla de la prenda: ");
        String talla = Validador.ingresarString(true);

        System.out.print("Ingrese el material de la prenda: ");
        String material = Validador.ingresarString(false);

        System.out.print("Ingrese el color de la prenda: ");
        String color = Validador.ingresarString(false);

        System.out.println("Ingrese el tipo de prenda:");
        String tipoPrenda = Validador.ingresarTipoPrenda();
        
        System.out.println("Ingrese el estilo de la prenda:");
        byte estilo = Validador.ingresarEstilo();

        NodoItem nuevoNodo = new NodoItem(nombre, precio, marca, talla, material, color, tipoPrenda, estilo);
        
        nuevoNodo.siguiente = primero;
        primero = nuevoNodo;
        size++;
        
        System.out.println("Prenda agregada exitosamente.");
        generarHistorial('a', "Producto " + nuevoNodo.nombre);
    }
    
    // Elimina un producto ingresado por teclado
    public void eliminarProducto() {        
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString(true);
        
        NodoItem actual = primero;
        NodoItem anterior = null;
        
        // Busca el producto en la lista
        while(actual != null) {
            if(actual.nombre.equalsIgnoreCase(nombre)) {
                // Se encontro el producto

                if(actual.equals(primero)) {
                    // Coincide con el primero, por lo tanto actualiza el primero al siguiente
                    primero = primero.siguiente;
                } else {
                    // No coincide con el primero, por lo tanto el anterior apunta al siguiente
                    // del actual, "saltandose" el actual.
                    anterior.siguiente = actual.siguiente;
                }
                size--;
                System.out.println("Se elimino el producto.");
                generarHistorial('e', "Producto " + nombre);
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
        
        // Si no se encontro el producto, se mostrara este mensaje
        System.out.println("El producto no existe.");
    }
    
    // Permite modificar cualquier atributo de un producto
    public void modificarProducto(String nombre) {
        NodoItem producto = buscarPorNombre(nombre);
        
        // Si el producto no existe, se cancela la operacion
        if (producto == null) {
            pausa();
            return; 
        }

        String[] opciones = {
            "Nombre", "Precio", "Marca", "Talla", "Material", 
            "Color", "Tipo de prenda", "Estilo"
        };

        while (true) {
            System.out.println(".:: Modificar producto " + nombre + " ::.");
            System.out.println("Seleccione el atributo a modificar:");
            
            for (int i = 0; i < opciones.length; i++) {
                System.out.println((i + 1) + ". " + opciones[i]);
            }
            System.out.println("0. Volver");
            System.out.print("Su opcion: ");

            int opcion = Validador.ingresarByte((byte) 0, (byte) 8);

            // Opcion 0 es volver al menu principal
            if (opcion == 0) break; 

            System.out.print("Ingrese nuevo valor para " + opciones[opcion - 1] + ": ");

            switch (opcion) {
                case 1:
                    producto.nombre = Validador.ingresarNombre(this);
                    generarHistorial('m', "Nombre del producto " + nombre + " actualizado a " + producto.nombre);
                    break;
                case 2:
                    producto.precio = Validador.ingresarInt(1, 1000000000);
                    generarHistorial('m', "Precio del producto " + producto.nombre + " actualizado a $" + producto.precio);
                    break;
                case 3:
                    producto.marca = Validador.ingresarString(true);
                    generarHistorial('m', "Marca del producto " + producto.nombre + " actualizada a " + producto.marca);
                    break;    
                case 4: 
                    producto.talla = Validador.ingresarString(true);
                    generarHistorial('m', "Talla del producto " + producto.nombre + " actualizada a " + producto.talla);
                    break;
                case 5: 
                    producto.material = Validador.ingresarString(false); 
                    generarHistorial('m', "Material del producto " + producto.nombre + " actualizado a " + producto.material);
                    break;
                case 6: 
                    producto.color = Validador.ingresarString(false);
                    generarHistorial('m', "Color del producto " + producto.nombre + " actualizado a " + producto.color);
                    break;
                case 7: 
                    System.out.println(); // Salto de linea para mostrar una lista
                    producto.tipoPrenda = Validador.ingresarTipoPrenda();
                    generarHistorial('m', "Tipo de prenda del producto " + producto.nombre + " actualizado a " + producto.tipoPrenda);
                    break;
                case 8: 
                    System.out.println(); // Salto de linea para mostrar una lista
                    producto.estilo = Validador.ingresarEstilo();
                    generarHistorial('m', "Estilo del producto " + producto.nombre + " actualizado a " + Validador.obtenerEstilo(producto.estilo));
                    break;
            }

            System.out.println("Producto modificado.");
            pausa();
        }
    }
    
    // Devuelve si existe un NodoItem que coincide con el nombre especificado.
    // Si no existe, devuelve null.
    public NodoItem buscarPorNombre(String nombre) {
        NodoItem nodo = primero;
        
        while(nodo != null) {
            if(nodo.nombre.equalsIgnoreCase(nombre)) {
                // Se encontro el producto
                return nodo;
            }
            nodo = nodo.siguiente;
        }
        
        System.out.println("El producto no existe.");
        return null;
    }
    
    // Muestra todos los elementos que coincidan con el estilo especificado
    public void listarPorEstilo() {
        System.out.println("Ingrese el estilo que desea buscar: ");
        byte estiloBuscado = Validador.ingresarEstilo();

        NodoItem actual = primero;
        boolean encontrado = false; // Indicará si se encontraron productos con el estilo buscado.

        System.out.println(".:: Productos con estilo '" + Validador.obtenerEstilo(estiloBuscado) + "' ::.");

        while (actual != null) { // mientras hayan nodos en la lista 
            if (actual.estilo == estiloBuscado) {
                mostrarProducto(actual);
                encontrado = true;
            }
            actual = actual.siguiente;
        }

        if (encontrado) {
            generarHistorial('c', "Productos por estilo " + Validador.obtenerEstilo(estiloBuscado));
        } else {
            System.out.println("No se encontraron elementos con el estilo especificado.");
        }
    }

    // Muestra, si existen, todos los elementos de la lista
    public void mostrar() {
        if(estaVacio()) {
            System.out.println("El inventario esta vacio.");
            return;
        }
        
        System.out.println(".:: Inventario ::.");
        
        NodoItem actual = primero;
        while(actual != null) {
            mostrarProducto(actual);
            actual = actual.siguiente;
        }
        
        generarHistorial('c', "Inventario");
    }
    
    // Muestra un elemento en particular
    public void mostrarProducto(NodoItem producto) {
        if (producto == null) {
            System.out.println("El producto no existe.");
            return;
        }

        System.out.println("Nombre: " + producto.nombre);
        System.out.println("Precio: $" + producto.precio);
        System.out.println("Marca: " + producto.marca);
        System.out.println("Talla: " + producto.talla);
        System.out.println("Material: " + producto.material);
        System.out.println("Color: " + producto.color);
        System.out.println("Tipo de Prenda: " + producto.tipoPrenda);
        System.out.println("Estilo: " + Validador.obtenerEstilo(producto.estilo));
        System.out.println("--------------------------------");
    }
    
    // Calcula el tamanio de la lista en bytes
    public short calcularTamanio() {
        if (estaVacio()) {
            return 21; // byte size + Overhead + Referencia
        }
        
        // Inicia con el tamano del byte size, el overhead y la referencia.
        short tamanio = 21;
        
        // Recorre la lista sumando el tamanio de cada producto
        NodoItem actual = primero;
        while(actual != null) {
            tamanio += calcularTamanioProducto(actual);
            actual = actual.siguiente;
        }
        
        return tamanio;
    }
    
    // Calcula el tamanio en bytes del producto especificado
    public short calcularTamanioProducto(NodoItem producto) {
        if(producto == null) {
            return 0;
        }
        
        // Inicia con las constantes de referencia propia (8), overhead (12),
        // referencia al siguiente (8) y los datos Precio (4) y Estilo (1).
        short tamanio = 33;
        
        // Calcula el tamanio de cada String considerando
        // Overhead + Referencia + (CantidadChar * 2)
        tamanio += (short) producto.nombre.length() * 2 + 20;
        tamanio += (short) producto.marca.length() * 2 + 20;
        tamanio += (short) producto.talla.length() * 2 + 20;
        tamanio += (short) producto.material.length() * 2 + 20;
        tamanio += (short) producto.color.length() * 2 + 20;
        tamanio += (short) producto.tipoPrenda.length() * 2 + 20;
        
        return tamanio;
    }
}