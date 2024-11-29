package com.proyectoed.inventario;

// Importa la funcion generarHistorial() desde Main.java
import static com.proyectoed.inventario.Main.generarHistorial;
import java.util.Scanner;

// Clase que contiene metodos para validar la entrada de datos
public class Validador {
    
    private static Scanner sc = new Scanner(System.in);
    public static ListaString tiposPrenda = new ListaString();
    
    // No hay necesidad de crear una instancia de esta clase por lo tanto es privada.
    private Validador(){};
    
    // Valida el ingreso de un byte dentro del rango [min, max]
    public static byte ingresarByte(byte min, byte max) {        
        boolean flag = false;
        byte num = 0;
        
        do {
            // Comprueba si hay un byte en el Scanner.
            // Si hay otro caracter: devuelve false
            // Si hay un byte: devuelve true
            if(sc.hasNextByte()) {
               num = sc.nextByte();
               
               // Verifica que el numero este dentro del rango [min, max]
               if(num >= min && num <= max) {
                   // El dato ingresado es correcto.
                   flag = true;
               } else {
                   System.out.println("Error: Valor no esta dentro del rango " + min + ", " + max + ".");
                   System.out.print("Intente de nuevo: ");
               }
            }
            else {
                // Descarta la entrada incorrecta.
                sc = new Scanner(System.in);
                System.out.println("Error: Valor incorrecto.");
                System.out.print("Intente de nuevo: ");
                
            }
        } while(!flag);
        
        
        sc.nextLine(); // Descarta el salto de linea
        return num;
    }
    
    // Valida el ingreso de un int dentro del rango [min, max]
    public static int ingresarInt(int min, int max) {
        boolean flag = false;
        int num = 0;
        
        do {            
            // Comprueba si hay un int en el Scanner.
            // Si hay otro caracter: devuelve false
            // Si hay un int: devuelve true
            if(sc.hasNextInt()) {
               num = sc.nextInt();
               
               // Verifica que el numero este dentro del rango [min, max]
               if(num >= min && num <= max) {
                   flag = true;
               } else {
                   System.out.println("Error: Valor no esta dentro del rango " + min + ", " + max + ".");
                   System.out.print("Intente de nuevo: ");
               }
            } else {
                sc = new Scanner(System.in); // Descarta la entrada incorrecta
                System.out.println("Error: Valor incorrecto.");
                System.out.print("Intente de nuevo: ");
            }
        } while(!flag);
        
        
        sc.nextLine(); // Descarta el salto de linea
        return num;
    }
    
    // Valida el ingreso de una palabra de solo letras y espacios.
    // Opcionalmente se permiten si conNumeros = true. (Nombre, Marca y Talla pueden contener numeros)
    // NOTA: Para el proposito del proyecto, se consideran maximo 30 caracteres en todos los atributos.
    public static String ingresarString(boolean conNumeros) {
        boolean flag;
        String str;
        
        do {
            flag = true;
            str = sc.nextLine();
            
            // Elimina los espacios al principio y al final
            str = str.trim();
            
            if(str.isEmpty()) {
                flag = false;
                System.out.println("Error: No se ingreso el dato.");
                System.out.print("Intente de nuevo: ");
            } else if(str.length() > 30) {
                flag = false;
                System.out.println("Error: El dato supera el limite de caracteres ("+ str.length() + "/30).");
                System.out.print("Intente de nuevo: ");
            } else {
                // Recorre el string caracter por caracter
                for(int i = 0; i < str.length(); i++) {
                    // Caracter en la posicion i del string.
                    char c = str.charAt(i);
                    
                    if(conNumeros) {
                        // Permitir letras, numeros y espacios
                        if(!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                            System.out.println("Error: Hay caracteres invalidos.");
                            System.out.print("Intente de nuevo: ");
                            flag = false;
                            break;
                        }
                    } else {
                        // Permitir letras y espacios
                        if(!Character.isLetter(c) && !Character.isWhitespace(c)) {
                            System.out.println("Error: Hay caracteres invalidos.");
                            System.out.print("Intente de nuevo: ");
                            flag = false;
                            break;
                        }
                    }
                }
            }
        } while(!flag);
        
        return str;
    }
    
    // Valida el ingreso de un nombre, verificando que este no exista en la lista.
    public static String ingresarNombre(ListaInventario lista) {
        String nombre;
        boolean existe;
        
        do {
            nombre = ingresarString(true);
            existe = false;
            
            if(!lista.estaVacio()) {
                NodoItem actual = lista.primero;
                
                while(actual != null) {
                    // equalsIgnoreCase se usa para comparar las cadenas sin considerar mayúsculas o minúsculas.
                    if (actual.nombre.equalsIgnoreCase(nombre)) {
                        System.out.println("El producto ya existe.");
                        System.out.print("Intente de nuevo: ");
                        existe = true;
                        break;
                    }
                    actual = actual.siguiente;
                }
            }
            
        } while(existe);
        
        return nombre;
    }

    // Muestra la lista de estilos y valida el ingreso un estilo
    public static byte ingresarEstilo() {        
        System.out.println(" 1. Casual    | 5. Urbano");
        System.out.println(" 2. Formal    | 6. Escolar");
        System.out.println(" 3. Deportivo | 7. Trabajo");
        System.out.println(" 4. Outdoor   | 8. Ropa interior");
        System.out.print("Su opcion: ");
        return ingresarByte((byte)1, (byte)8);
    }
    
    // Devuelve el nombre del estilo segun la opcion ingresada
    public static String obtenerEstilo(byte estilo) {
        switch(estilo) {
            case 1: return "Casual";
            case 2: return "Formal";
            case 3: return "Deportivo";
            case 4: return "Outdoor";
            case 5: return "Urbano";
            case 6: return "Escolar";
            case 7: return "Trabajo";
            case 8: return "Ropa interior";
            default: return "Casual";
        }
    }
    
    // Valida el ingreso de un tipo de prenda guardado en lista. Ademas, permite
    // agregar un nuevo tipo de prenda llamando a registrarTipoPrenda()
    public static String ingresarTipoPrenda() {
        tiposPrenda.mostrar(true);
        System.out.println(" 0. Agregar un tipo de prenda");
        
        System.out.print("Su opcion: ");
        byte opcion = Validador.ingresarByte((byte)0, tiposPrenda.size);
        
        // Si la opcion es 0, se agrega un nuevo tipo de prenda y se devuelve el mismo.
        if(opcion == 0) {
            return registrarTipoPrenda();
        }
        
        // Busca el tipo de prenda segun la opcion ingresada
        NodoString actual = tiposPrenda.primero;
        for(byte i = 1; i < opcion; i++) actual = actual.siguiente;
        return actual.data;
    }
    
    // Agrega un nuevo tipo de prenda
    public static String registrarTipoPrenda()  {
        String tipoNuevo;
        boolean existe = false;
        
        System.out.print("Ingrese el nuevo tipo de prenda: ");
        do {
            tipoNuevo = Validador.ingresarString(false);
            
            if(tiposPrenda.contiene(tipoNuevo)) {
                // El tipo de prenda ingresado ya existe en la lista, por lo tanto
                // se debe ingresar uno distinto para evitar duplicaciones.
                System.out.println("El tipo de prenda \"" + tipoNuevo + "\" ya existe");
                System.out.print("Intente de nuevo: ");
                existe = true;
            } else {
                // El tipo de prenda no existe, por lo tanto se agrega.
                tiposPrenda.agregar(tipoNuevo);
                existe = false;
                System.out.println("El tipo de prenda se agrego con exito.");
                generarHistorial('a', "Tipo de prenda " + tipoNuevo);
            }
        } while(existe);
        
        return tipoNuevo;
    }
}