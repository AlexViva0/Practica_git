import java.util.Scanner;

public class ValidadorSimple {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String linea = sc.nextLine().trim();
            if (linea.equals("salir")) break;
            System.out.println(esValida(linea) ? "CORRECTO" : "INCORRECTO");
        }

        sc.close();
    }

    static boolean esValida(String linea) {
        String[] p = linea.split(" ");

        // 1. Debe tener 4 partes:  tipo  nombre  =  valor;
        if (p.length != 4) return false;

        String tipo   = p[0];
        String nombre = p[1];
        String igual  = p[2];
        String valor  = p[3];

        // 2. El operador debe ser =
        if (!igual.equals("=")) return false;

        // 3. Debe terminar con punto y coma
        if (!valor.endsWith(";")) return false;
        valor = valor.substring(0, valor.length() - 1);

        // 4. El nombre debe ser valido
        if (!nombreValido(nombre)) return false;

        // 5. El valor debe coincidir con el tipo
        if (tipo.equals("int"))     return esEntero(valor);
        if (tipo.equals("double"))  return esDecimal(valor);
        if (tipo.equals("boolean")) return valor.equals("true") || valor.equals("false");
        if (tipo.equals("String"))  return esTexto(valor);

        return false;   // tipo desconocido
    }

    static boolean nombreValido(String nombre) {
        if (nombre.isEmpty()) return false;
        if (!Character.isLetter(nombre.charAt(0))) return false;

        for (int i = 1; i < nombre.length(); i++) {
            char c = nombre.charAt(i);
            if (!Character.isLetterOrDigit(c) && c != '_') return false;
        }
        return true;
    }

    static boolean esEntero(String valor) {
        try {
            Integer.parseInt(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean esDecimal(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean esTexto(String valor) {
        return valor.length() >= 2
            && valor.startsWith("\"")
            && valor.endsWith("\"");
    }
}
