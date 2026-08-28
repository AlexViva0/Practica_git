
public class AnalizadorSemantico {

    public static void main(String[] args) {

        String codigo = "double b = a + 2.5;";

        analizar(codigo);
    }

    static void analizar(String linea) {
        linea = linea.trim();

        if (!linea.endsWith(";"))                        { malaSintaxis(); return; }
        String cuerpo = linea.substring(0, linea.length() - 1).trim();

        int pos = cuerpo.indexOf('=');
        if (pos < 0)                                     { malaSintaxis(); return; }

        String izquierda = cuerpo.substring(0, pos).trim();
        String derecha   = cuerpo.substring(pos + 1).trim();

        String[] partes = izquierda.split("\\s+");
        if (partes.length != 2)                          { malaSintaxis(); return; }

        String tipo   = partes[0];
        String nombre = partes[1];

        if (!esTipoValido(tipo))                         { malaSintaxis(); return; }
        if (!nombre.matches("[a-zA-Z][a-zA-Z0-9_]*"))    { malaSintaxis(); return; }

        String[] ops = derecha.split("\\+");
        if (ops.length != 2)                             { malaSintaxis(); return; }

        String tIzq = tipoDelValor(ops[0].trim());
        String tDer = tipoDelValor(ops[1].trim());
        if (tIzq == null || tDer == null)                { malaSintaxis(); return; }

        System.out.println("SINTAXIS:  CORRECTA");

        // ---------- 2. SEMANTICA: el significado ----------
        String tSuma = tipoDeLaSuma(tIzq, tDer);

        if (tSuma == null) {
            System.out.println("SEMANTICA: INCORRECTA");   // no se pueden sumar
        } else if (!seAsigna(tSuma, tipo)) {
            System.out.println("SEMANTICA: INCORRECTA");   // la suma no cabe en el tipo
        } else {
            System.out.println("SEMANTICA: CORRECTA");
        }
    }

    static void malaSintaxis() {
        System.out.println("SINTAXIS:  INCORRECTA");
        System.out.println("SEMANTICA: INCORRECTA");
    }

    static boolean esTipoValido(String t) {
        return t.equals("int") || t.equals("double")
            || t.equals("boolean") || t.equals("String");
    }

    // Deduce el tipo de un literal
    static String tipoDelValor(String v) {
        if (v.matches("[+-]?\\d+"))          return "int";
        if (v.matches("[+-]?\\d+\\.\\d+"))   return "double";
        if (v.equals("true") || v.equals("false")) return "boolean";
        if (v.matches("\"[^\"]*\""))         return "String";
        return null;                          // no es un valor valido
    }

    // Reglas del operador +   (null = no se puede sumar)
    static String tipoDeLaSuma(String a, String b) {
        if (a.equals("String")  || b.equals("String"))  return "String";
        if (a.equals("boolean") || b.equals("boolean")) return null;
        if (a.equals("double")  || b.equals("double"))  return "double";
        return "int";
    }

    // Reglas de asignacion  ( int cabe en double: coercion implicita )
    static boolean seAsigna(String origen, String destino) {
        if (origen.equals(destino)) return true;
        return origen.equals("int") && destino.equals("double");
    }
}
