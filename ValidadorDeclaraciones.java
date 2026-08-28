import java.util.Scanner;

public class ValidadorDeclaraciones {

    // Tipos de las dos variables declaradas
    static String nombre1, tipo1;
    static String nombre2, tipo2;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            // --- Declaracion 1 ---
            String d1 = sc.nextLine().trim();
            if (d1.equalsIgnoreCase("salir")) break;
            if (!declarar(d1, 1)) { System.out.println("ERROR DE SINTAXIS"); continue; }

            // --- Declaracion 2 ---
            String d2 = sc.nextLine().trim();
            if (d2.equalsIgnoreCase("salir")) break;
            if (!declarar(d2, 2)) { System.out.println("ERROR DE SINTAXIS"); continue; }

            // --- Suma ---
            String suma = sc.nextLine().trim();
            if (suma.equalsIgnoreCase("salir")) break;
            System.out.println(evaluarSuma(suma));
        }

        sc.close();
    }

    // Valida "tipo nombre = valor ;" y guarda el tipo en la tabla de simbolos
    static boolean declarar(String linea, int cual) {
        if (!linea.endsWith(";")) return false;
        String cuerpo = linea.substring(0, linea.length() - 1).trim();

        int pos = cuerpo.indexOf('=');
        if (pos < 0) return false;

        String izquierda = cuerpo.substring(0, pos).trim();
        String valor     = cuerpo.substring(pos + 1).trim();
        if (valor.isEmpty()) return false;

        String[] partes = izquierda.split("\\s+");
        if (partes.length != 2) return false;

        String tipo   = partes[0];
        String nombre = partes[1];

        if (!nombre.matches("[a-zA-Z][a-zA-Z0-9_]*")) return false;

        boolean ok;
        switch (tipo) {
            case "int":     ok = valor.matches("[+-]?\\d+");            break;
            case "double":  ok = valor.matches("[+-]?\\d+(\\.\\d+)?");  break;
            case "boolean": ok = valor.equals("true") || valor.equals("false"); break;
            case "String":  ok = valor.matches("\"[^\"]*\"");           break;
            default:        return false;
        }
        if (!ok) return false;

        // Guardar en la tabla de simbolos
        if (cual == 1) { nombre1 = nombre; tipo1 = tipo; }
        else           { nombre2 = nombre; tipo2 = tipo; }
        return true;
    }

    // Valida "a + b" y devuelve el tipo del resultado
    static String evaluarSuma(String expr) {
        String[] p = expr.split("\\s*\\+\\s*");
        if (p.length != 2) return "ERROR DE SINTAXIS";

        String izq = p[0].trim();
        String der = p[1].trim();

        String tIzq = buscar(izq);
        String tDer = buscar(der);
        if (tIzq == null || tDer == null) return "ERROR DE SINTAXIS";

        return tipoResultado(tIzq, tDer);
    }

    // Consulta a la tabla de simbolos
    static String buscar(String nombre) {
        if (nombre.equals(nombre1)) return tipo1;
        if (nombre.equals(nombre2)) return tipo2;
        return null;
    }

    // Reglas de compatibilidad del operador +
    static String tipoResultado(String a, String b) {
        if (a.equals("String")  || b.equals("String"))  return "String";
        if (a.equals("boolean") || b.equals("boolean")) return "ERROR DE TIPOS";
        if (a.equals("double")  || b.equals("double"))  return "double";
        return "int";
    }
}
