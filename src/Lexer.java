import java.util.HashMap;

public class Lexer {
    static String[] reserved;
    static HashMap<String, String> oper;
    public static void main(String args[])
    {
        reserved = new String[]{
                "log",
                "false",
                "true",
                "while",
                "if",
                "importar",
                "for",
                "funcion",
                "retorno",
                "end",
        };
        oper = new HashMap<>();
        oper.put("{","token_llave_izq");
        oper.put("}","token_llave_der");
        oper.put("#","token_com");
        oper.put("[","token_cor_izq");
        oper.put("]","token_cor_der");
        oper.put("(","token_par_izq");
        oper.put(")","token_par_der");
        oper.put(">","token_mayor");
        oper.put("<","token_menor");
        oper.put(">=","token_mayor_igual");
        oper.put("<=","token_menor_igual");
        oper.put("in","token_in");
        oper.put("==","token_igual_num");
        oper.put(".","token_point");
        oper.put("!=","token_diff_num");
        oper.put("&&","token_and");
        oper.put("||","token_or");
        oper.put("!","token_not");
        oper.put("+","token_mas");
        oper.put("-","token_menos");
        oper.put("*","token_mul");
        oper.put("/","token_div");
        oper.put("%","token_mod");
        oper.put("^","token_pot");
    }
}
