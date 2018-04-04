package lenguajes.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AnalizadorLexico {


    public AnalizadorLexico(String archivoEntrada) {

        operadoresEspeciales= new HashMap<String,String>();
        operadoresEspecialesDobles= new HashMap<String,String>();
        palabraReservada = new ArrayList<String>();
        columna = 0;
        fila = 0;

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

        return "exitoso";
    }



    public static String manejadorTexto (String linea) {
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
        	seleccionarToken(cadenaAEvaluar);
        return cadenaAEvaluar;
    }

    private static String iniciarCaracterDoble(String linea, int i, char caracterActual) {

        String caracterDoble = "" + caracterActual;

        if (i < linea.length() - 1) {

            caracterDoble ="" + caracterActual + linea.charAt(i+1);

        }

        return caracterDoble;

    }

    private static int separarOperadorEspecial(String linea, int indiceInicial, int i, String caracterActual, int aumento) {

        aumentarColumna();
        String cadenaAEvaluar = linea.substring(indiceInicial,i);
        cadenaAEvaluar = cadenaAEvaluar.replaceAll("\\s+","");
        
        if (!cadenaAEvaluar.isEmpty())
        	seleccionarToken(cadenaAEvaluar);
        
        indiceInicial = indiceInicial + i + aumento;
        aumentarColumna();
        imprimirOperadorEspecial(caracterActual);
        
        return indiceInicial;

    }

    private static void imprimirOperadorEspecial(String operadorEspecial) {
        if (operadoresEspecialesDobles.containsKey(operadorEspecial)) {

            System.out.println("<" +
                    operadoresEspecialesDobles.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");

        }else {

            System.out.println("<" +
                    operadoresEspeciales.get(operadorEspecial) +
                    "," + Integer.toString(fila) +
                    "," + Integer.toString(columna) +">");

        }
    }
    
    private static void imprimirError() {
    	
    	System.out.println(">>> Error lexico (linea: " + Integer.toString(fila) + ", posicion: " + Integer.toString(columna) + ")");
    	System.exit(0);
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

    private static void seleccionarToken(String substring) {

        System.out.println("Entro a Seleccionar Token: " + substring);


        if (esCadena(substring)) {
            imprimirSalida("token_string");
            aumentarColumna();
            return;
        } else if (esNumero(substring)) {

            /*TODO Imprimir token
             * 	   Aumentar Columna
             */

        } else if (esComentario(substring)) {

            /*	   Imprimir token
             * 	   Aumentar Columna
             *
             */

            imprimirSalida("token_com");
            aumentarColumna();
            return;

        } else if (esIdentificador(substring)== "id") {
            imprimirSalida("token_id");
            aumentarColumna();
            return;

        }
        else if (esIdentificador(substring) != "id") {
            imprimirSalida("token_" + esIdentificador(substring));
            aumentarColumna();
            return;

        }
          else {

            imprimirError();
            return;
        }


		return;

    }

	private static void imprimirSalida(String salida) {
		 System.out.println("<" +
				 salida +
		        "," + Integer.toString(fila) +
		        "," + Integer.toString(columna) +">");
	}

    public static boolean esCadena(String substring) {

        if (substring.startsWith("\"") && substring.endsWith("\""))
            return true;
    	
    /*	else if (substring.charAt(0) != substring.charAt(substring.length()-1))
    		imprimirError();*/
    	
        return false;
    }

    public static boolean esComentario(String substring) {
    	
        if (substring.startsWith("#")) 
            return true;
        
    	return false;
    }

    private static boolean esNumero(String substring) {
		/* TODO Numero
		 * Debe manejar errores
		 * Diferenciar entre doubles y enteros
		 */
        return false;
    }

    public static String esIdentificador(String substring) {

        String str = substring;


        int i = 0;
        while(i <str.length()) {
            char t = str.charAt(i);
            if (Character.isLetter(t)) {
                String aux = "";
                aux += t;
                int j = i + 1;

                if ( str.length()==1){
                   return "id";
                }

                while (Character.isLetterOrDigit(str.charAt(j))) {

                    aux += str.charAt(j);
                    j++;
                    if (j == str.length()) break;
                }
                i = j;

                if (palabraReservada.contains(aux)) return aux;


                else return "id";
            }
        }

        return "id";
    }




    private static HashMap <String, String> operadoresEspecialesDobles;
    private static HashMap <String,String> operadoresEspeciales;
    private static ArrayList <String> palabraReservada;
    private static int columna;
    private static int fila;
}