package com.proyectoed.inventario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    
    private static ListaInventario inventario = new ListaInventario();
    private static ListaString historial = new ListaString();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Registra el inicio del programa en el historial
        generarHistorial('0', "");
        
        // Agrega tipos de prenda por defecto
        Validador.tiposPrenda.agregar("Camiseta");
        Validador.tiposPrenda.agregar("Pantalon");
        Validador.tiposPrenda.agregar("Vestido");
        Validador.tiposPrenda.agregar("Poleron");
        
        byte opcion;

        do {
            System.out.println("..:: Menu Principal ::..");
            System.out.print("Productos en inventario: " + inventario.size + "/60 ");
            System.out.println("(" + inventario.calcularTamanio() + " bytes)");
            
            System.out.println("1. Ver inventario      | 6. Consultar talla");
            System.out.println("2. Agregar producto    | 7. Consultar material");
            System.out.println("3. Modificar producto  | 8. Consultar tamanio de producto");
            System.out.println("4. Eliminar producto   | 9. Registrar tipo de prenda");
            System.out.println("5. Listar por estilo   | 10. Mostrar historial");
            System.out.println("0. Salir\n");
            
            System.out.print("Ingrese una opcion: ");
            opcion = Validador.ingresarByte((byte) 0, (byte) 10);

            // Las opciones 3 a 8 no se pueden realizar si el inventario esta vacio
            if (opcion >= 3 && opcion <= 8 && inventario.estaVacio()) {
                System.out.println("El inventario esta vacio.");
                pausa();
                continue; // Salta a la siguiente iteracion, evitando que se ejecute el switch
            }

            switch (opcion) {
                case 0: 
                    // Salir del programa
                    System.out.println("Saliendo..."); 
                    break;
                case 1: 
                    // Ver inventario
                    inventario.mostrar(); 
                    break;
                case 2: 
                    // Agregar producto
                    inventario.agregarProducto(); 
                    break;
                case 3:
                    // Modificar producto
                    System.out.print("Ingrese el nombre del producto a modificar: ");
                    String nombre = Validador.ingresarString(true);
                    
                    inventario.modificarProducto(nombre);
                    break;
                case 4: 
                    // Eliminar producto
                    inventario.eliminarProducto(); 
                    break;
                case 5: 
                    // Listar por estilo
                    inventario.listarPorEstilo(); 
                    break;
                case 6: 
                    // Consultar talla
                    consultarTalla(); 
                    break;
                case 7: 
                    // Consultar material
                    consultarMaterial(); 
                    break;
                case 8: 
                    // Consultar tamanio de producto
                    consultarTamanioProducto();
                    break;
                case 9: 
                    // Registrar tipo de prenda
                    Validador.registrarTipoPrenda();
                    break;
                case 10:
                    // Mostrar historial
                    System.out.println(".:: Historial ::.");
                    historial.mostrar(false);
                    break;
                default:
                    // Opcion no valida
                    System.out.println("Opcion invalida, por favor intentelo nuevamente.");
                    break;
            }
            
            // Evita pausar cuando se cierra el programa o cuando se modifica un producto
            // ya que el menu de modificacion ya contiene una pausa.
            if(opcion != 0 && opcion != 3) pausa();
            
        } while (opcion != 0);
    }
    
    // Consulta la talla del producto especificado
    public static void consultarTalla() {        
        NodoItem item;

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString(true);

        item = inventario.buscarPorNombre(nombre);

        if(item != null){
            System.out.println("La talla del producto " + item.nombre + " es: " + item.talla);
            generarHistorial('c', "Talla del producto " + item.nombre + " = " + item.talla); 
        }
    }
    
    // Consulta el material del producto especificado
    public static void consultarMaterial() {
        NodoItem item;

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString(true);

        item = inventario.buscarPorNombre(nombre);

        if(item != null){
            System.out.println("El material del producto " + item.nombre + " es: " + item.material);
            generarHistorial('c', "Material del producto " + item.nombre + " = " + item.material); 
        }
    }
    
    // Consulta el tamanio del producto especificado
    public static void consultarTamanioProducto() {
        NodoItem item;
        
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = Validador.ingresarString(true);

        item = inventario.buscarPorNombre(nombre);

        if(item != null) {
            short tam = inventario.calcularTamanioProducto(item);
            System.out.println("El tamanio del producto " + nombre + " es " + tam + " bytes.");
            generarHistorial('c', "Tamanio de producto " + nombre + " = " + tam + " bytes");
        }
    }
    
    // Pausa el programa hasta que se presione Enter y luego imprime saltos
    // de linea para "limpiar" la consola.
    public static void pausa() {
        System.out.print("\nPresione Enter para continuar...");
        sc.nextLine();
        
        // "Limpia" la consola
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
    }
    
    // Guarda las acciones realizadas en una ListaString a modo de historial
    // Acciones: a = Agregar, e = Eliminar, c = Consultar, m = Modificar
    //           0 = Inicio programa
    public static void generarHistorial(char accion, String detalle) {
        // Obtiene la hora y fecha actual en formato Dia-Mes-Anio Hora:Mins:Segs
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fecha = LocalDateTime.now().format(formatoFecha);

        String mensaje ="[" + fecha + "] ";
        
        switch(accion) {
            case '0': mensaje += "Programa iniciado.";     break;
            case 'a': mensaje += "Agregar   : " + detalle; break;
            case 'e': mensaje += "Eliminar  : " + detalle; break;
            case 'c': mensaje += "Consultar : " + detalle; break;
            case 'm': mensaje += "Modificar : " + detalle; break;
            default: mensaje += detalle; break;
        }
        
        historial.agregar(mensaje);
    }
}