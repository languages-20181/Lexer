package lenguajes.code;

import com.sun.org.apache.xml.internal.utils.SystemIDResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class AnalizadorLexico {


    public AnalizadorLexico(String archivoEntrada) throws IOException {

        operadoresEspeciales= new HashMap<String,String>();
        operadoresEspecialesDobles= new HashMap<String,String>();
        palabraReservada = new ArrayList<String>();
        queueAux = new LinkedList<>();
        columna = 0;
        fila = 0;
        output = new File("output.txt");
        writer = new BufferedWriter(new FileWriter(output));

        iniciarOperadoresEspeciales();
        iniciarPalabaraReservada();
        try {
            System.out.println(entrada(archivoEntrada));
        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();
        }
    }

    public void iniciarOperadoresEspeciales() {

        operadoresEspeciales.put("{","token_llave_izq");
        operadoresEspeciales.put("}","token_llave_der");
//      operadoresEspeciales.put("#","token_com"); No se considera ṕrque se analiza en el Switch (seleccionarToken)
        operadoresEspeciales.put("[","token_cor_izq");
        operadoresEspeciales.put("]","token_cor_der");
        operadoresEspeciales.put("(","token_par_izq");
        operadoresEspeciales.put(")","token_par_der");
        operadoresEspeciales.put(">","token_mayor");
        operadoresEspeciales.put("<","token_menor");
        operadoresEspeciales.put("in","token_in");
        operadoresEspeciales.put("=", "token_equal");
//      operadoresEspeciales.put(".","token_point"); No se considera porque se analiza en el Switch (seleccionarToken)
        operadoresEspeciales.put("!","token_not");
        operadoresEspeciales.put("+","token_mas");
        operadoresEspeciales.put("-","token_menos");
        operadoresEspeciales.put("*","token_mul");
        operadoresEspeciales.put("/","token_div");
        operadoresEspeciales.put("%","token_mod");
        operadoresEspeciales.put("^","token_pot");
        operadoresEspeciales.put(":", "token_colon");
        operadoresEspeciales.put(",","token_comma");

        operadoresEspecialesDobles.put(">=","token_mayor_igual");
        operadoresEspecialesDobles.put("<=","token_menor_igual");
        operadoresEspecialesDobles.put("!=","token_diff_num");
        operadoresEspecialesDobles.put("&&","token_and");
        operadoresEspecialesDobles.put("||","token_or");
        operadoresEspecialesDobles.put("==","token_igual_num");

    }

    public void iniciarPalabaraReservada() {
        palabraReservada.add("log");
        palabraReservada.add("true");
        palabraReservada.add("false");
        palabraReservada.add("while");
        palabraReservada.add("importar");
        palabraReservada.add("log");
        palabraReservada.add("if");
        palabraReservada.add("for");
        palabraReservada.add("funcion");
        palabraReservada.add("retorno");
        palabraReservada.add("end");
        palabraReservada.add("else");

    }




    public String entrada (String archivoEntrada) throws FileNotFoundException, IOException{

        FileReader archivoTxt = new FileReader(archivoEntrada);
        BufferedReader lector = new BufferedReader (archivoTxt);

        String lineaActual = lector.readLine();

        while (lineaActual != null) {

            aumentarFila();
            cambiarColumna(0);
            manejadorTexto(lineaActual);
            lineaActual = lector.readLine();

        }

        writer.close();
        return "Successful!!";
    }

    public static void filePrintLine(String line) throws IOException{
        writer.write(line+'\n');
    }

    public static String manejadorTexto (String linea) throws IOException {
        String cadenaAEvaluar = new String();
        int indiceInicial = 0;

        for (int i = 0; i < linea.length(); i++) {

            char caracterActual = linea.charAt(i);
            String caracterDoble = iniciarCaracterDoble(linea, i, caracterActual);
            if (operadoresEspecialesDobles.containsKey(caracterDoble)) {
                indiceInicial = separarOperadorEspecial(linea, indiceInicial, i, caracterDoble, 2);
                i++;

            }else if (operadoresEspeciales.containsKey("" + caracterActual )) {
                indiceInicial = separarOperadorEspecial(linea, indiceInicial, i, "" +caracterActual, 1);
            }


        }
        aumentarColumna();

        if(indiceInicial > linea.length()) {
            /*
            seleccionarToken(linea.substring(indiceInicial-1,linea.length()));
            return linea.substring(indiceInicial-1,linea.length());
            */
            return "";
        }
        cadenaAEvaluar = linea.substring(indiceInicial,linea.length());
        cadenaAEvaluar = cadenaAEvaluar.replaceAll("\\s+","");

        if (!cadenaAEvaluar.isEmpty())
            seleccionarToken(new String[]{cadenaAEvaluar,""});
        return cadenaAEvaluar;


    }

    private static String iniciarCaracterDoble(String linea, int i, char caracterActual) {

        String caracterDoble = "" + caracterActual;

        if (i < linea.length() - 1) {
            caracterDoble ="" + caracterActual + linea.charAt(i+1);
        }

        return caracterDoble;

    }

    private static int separarOperadorEspecial(String linea, int indiceInicial, int i, String caracterActual, int aumento) throws IOException {

        String cadenaAEvaluar = linea.substring(indiceInicial,i);
        aumentarColumna();

        if(cadenaAEvaluar.contains(" ") && cadenaAEvaluar.contains("if") &&
                !cadenaAEvaluar.startsWith("\"") && !cadenaAEvaluar.startsWith("#")){
            String []  spl= cadenaAEvaluar.split(" ");

            for(int m = 0; m < spl.length;m++) queueAux.add(spl[m]);
            int cuenta= 0;

            while (!queueAux.isEmpty()){
                String aux = queueAux.poll();
                seleccionarToken(new String[]{aux,""});
                cuenta += aux.length();
            }

            indiceInicial =i + aumento;
            imprimirOperadorEspecial(caracterActual);
            return indiceInicial;
            /*
            seleccionarToken(queueAux.poll());
            seleccionarToken(queueAux.poll());
            indiceInicial = indiceInicial + (i+1) + aumento;
            imprimirOperadorEspecial(caracterActual);
            return indiceInicial;*/

        }

        String raw = cadenaAEvaluar;
        cadenaAEvaluar = cadenaAEvaluar.replaceAll("\\s+",""); //Removes spaces

        if (!cadenaAEvaluar.isEmpty())
            if (esCadena(cadenaAEvaluar))
                seleccionarToken(new String[]{raw,""});
            else
                seleccionarToken(new String[]{cadenaAEvaluar,""});

        indiceInicial = i + aumento;
        imprimirOperadorEspecial(caracterActual);

        return indiceInicial;

    }

    private static void imprimirOperadorEspecial(String operadorEspecial)  throws IOException{

        if (operadoresEspecialesDobles.containsKey(operadorEspecial)) {

            System.out.println("<" +
                    operadoresEspecialesDobles.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");
            filePrintLine("<" +
                    operadoresEspecialesDobles.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");

        }else {

            System.out.println("<" +
                    operadoresEspeciales.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");
            filePrintLine("<" +
                    operadoresEspeciales.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");

        }
    }

    private static void imprimirError(boolean exit) throws IOException {

        filePrintLine(">>> Error lexico (linea: " + Integer.toString(fila) + ", posicion: " + Integer.toString(columna) + ")");
        if (exit)
        {
            writer.close();
            System.exit(0);
        }
    }

    private static void aumentarColumna() {

        columna ++;

    }

    private void cambiarColumna(int i) {
        columna = 0;
    }

    private void aumentarFila() {
        fila++;
    }

    private static String[] manejarDeteccion(String substring) throws IOException {
        aumentarColumna();
        if (substring.length() > 0) {
            aumentarColumna();
            return seleccionarToken(new String[]{substring,""});
            //imprimirError(false);
            //return seleccionarToken(new String[]{substring[1],""});
        }
        return null;
    }

    private static String[] seleccionarToken(String[] substring) throws IOException{

        System.out.println(substring[0]+" "+substring[1]);

        String token = substring[0];

        String identificador = esIdentificador(token);

        if (esCadena(token)) {
            imprimirSalida("token_cadena,"+token);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;
        } else if (esDecimal(token)) {
            imprimirSalida("token_double,"+token);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;
        } else if(esEntero(token)){
            imprimirSalida("token_int,"+token);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;
        } else if (esComentario(token)) {

            /*     Imprimir token
             *
             */

            //imprimirSalida("token_com,"+token);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;

        } else if (identificador == "id") {
            imprimirSalida("id,"+token);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;

        }
        else if (identificador != "0") {
            imprimirSalida(identificador);
            String[] newToken = manejarDeteccion(substring[1]);
            if (newToken != null) return newToken;

        } else {

            if(token.length() >= 1) {
                seleccionarToken(
                        new String[]{
                                token.substring(0, token.length() - 1),
                                String.valueOf(token.charAt(token.length() - 1)).concat(substring[1])
                        }
                );
            } else {
                imprimirError(true);
                return new String[] {"",substring[1]};
            }

        }

        return new String[] {token,substring[1]};
    }

    private static void imprimirSalida(String salida) throws IOException {
        filePrintLine("<" +
                salida +
                "," + Integer.toString(fila) +
                "," + Integer.toString(columna) +">");
    }

    public static boolean esCadena(String substring) {

        if (substring.startsWith("\"") && substring.endsWith("\"") && substring.length() > 1)
            return true;

    /*  else if (substring.charAt(0) != substring.charAt(substring.length()-1))
            imprimirError();*/

        return false;
    }

    public static boolean esComentario(String substring) {

        if (substring.startsWith("#"))
            return true;

        return false;
    }

    public static boolean esDecimal(String substring) {

        try
        {
            Double numero= Double.parseDouble(substring);
            if (numero % 1 == 0)  return false;
            else return true;

        }
        catch(NumberFormatException nfe)
        {
            return false;
        }

    }

    public static boolean esEntero(String substring) {

        try
        {
            int numero= Integer.parseInt(substring);
            if (numero % 1 == 0)  return true;
            else return false;

        }
        catch(NumberFormatException nfe) {
            return false;
        }


    }



    public static String esIdentificador(String substring) {

        String str = substring;
        int i = 0;

        if(str.length() == 0) return "0";

        while(i <str.length()) {
            char t = str.charAt(i);
            if (Character.isLetter(t)) {
                String aux = "";
                aux += t;
                int j = i + 1;

                if ( str.length()==1) return "id";


                while (true) {
                    if(!Character.isLetterOrDigit(str.charAt(j)))
                        return "0";

                    aux += str.charAt(j);
                    j++;
                    if (j == str.length()) break;
                }
                i = j;

                /* Should this be evaluated first for an optimal performance? */
                if (palabraReservada.contains(aux)) return aux;
                else return "id";
            } else {
                return "0"; //This means that it's not an identifier nor a reserved word
            }
        }

        return "id";
    }

    private static HashMap <String, String> operadoresEspecialesDobles;
    private static HashMap <String,String> operadoresEspeciales;
    private static ArrayList <String> palabraReservada;
    private static Queue<String> queueAux;
    private static int columna;
    private static int fila;
    private static File output;
    private static BufferedWriter writer;
}