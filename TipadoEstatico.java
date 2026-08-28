// TIPADO ESTATICO - Java
// Quita el "//" en vivo: el error aparece en ROJO sin ejecutar nada.

public class TipadoEstatico {
    public static void main(String[] args) {

        // 1. El tipo vive en la VARIABLE
        int x = 5;
        // x = "hola";        // <-- ERROR: incompatible types

        // 2. Sintaxis perfecta, semantica invalida
        // int valor = "cadena";

        // 4. Inferencia NO es dinamico
        var y = 5;
        // y = "hola";        // <-- ERROR: y ya es int

        System.out.println("x = " + x + ", y = " + y);
    }
}
