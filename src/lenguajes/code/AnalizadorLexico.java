package lenguajes.code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class AnalizadorLexico {
	
		
	public AnalizadorLexico(String archivoEntrada) {
		
		operadoresEspeciales= new HashMap<String,String>();
		columna = 0;
		fila = 0;
		
		iniciarOperadoresEspeciales();
		/*
		try {
			System.out.println(entrada(archivoEntrada));
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		*/
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
        operadoresEspeciales.put(">=","token_mayor_igual");
        operadoresEspeciales.put("<=","token_menor_igual");
        operadoresEspeciales.put("in","token_in");
        operadoresEspeciales.put("==","token_igual_num");
//      operadoresEspeciales.put(".","token_point"); No se considera porque se analiza en el Switch (seleccionarToken)
        operadoresEspeciales.put("!=","token_diff_num");
        operadoresEspeciales.put("&&","token_and");
        operadoresEspeciales.put("||","token_or");
        operadoresEspeciales.put("!","token_not");
        operadoresEspeciales.put("+","token_mas");
        operadoresEspeciales.put("-","token_menos");
        operadoresEspeciales.put("*","token_mul");
        operadoresEspeciales.put("/","token_div");
        operadoresEspeciales.put("%","token_mod");
        operadoresEspeciales.put("^","token_pot");
		
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
		
		int indiceInicial = 0;
		for (int i = 0; i < linea.length(); i++) {
			char caracterActual = linea.charAt(i);
			if (operadoresEspeciales.containsKey("" + caracterActual )) {
				
				AumentarColumna();
				seleccionarToken(linea.substring(indiceInicial,i));
				indiceInicial = indiceInicial + i + 1;
				AumentarColumna();
				imprimirOperadorEspecial("" + caracterActual);
				
			}
		}
		if(indiceInicial > linea.length()) {
			seleccionarToken(linea.substring(indiceInicial-1,linea.length()));
			return linea.substring(indiceInicial-1,linea.length());
		}
		seleccionarToken(linea.substring(indiceInicial,linea.length()));
		return linea.substring(indiceInicial,linea.length());
	}
	
	
	
	
	private static void imprimirOperadorEspecial(String operadorEspecial) {
		System.out.println("<" +
				operadoresEspeciales.get(operadorEspecial) +
				"," + Integer.toString(fila) +
				"," + Integer.toString(columna) +">");
	}

	private static void AumentarColumna() {
		
		columna ++;
		
	}
	
	private void cambiarColumna(int i) {
		
		columna = 0;
		
	}
	
	private void aumentarFila() {
		fila++;
	}

	private static void seleccionarToken(String substring) {
		
		if (esIdentificador(substring)) {
			
			/*TODO Imprimir token
			 * 	   Aumentar Columna
			*/
			
		} else if (esNumero(substring)) {
			
			/*TODO Imprimir token
			 * 	   Aumentar Columna
			*/
			
		} else if (esCadena(substring)) {
			
			/*TODO Imprimir token
			 * 	   Aumentar Columna
			 * 	   Se refiere a cadenas como "hola"
			*/
			
		} else if (esComentario(substring)) {
			
			/*TODO Imprimir token
			 * 	   Aumentar Columna
			*/
			
		} else {
			return;
		}
		
		
		System.out.println("Entro a Seleccionar Token: " + substring);
		
	}









	private static boolean esComentario(String substring) {
		/*TODO Comentarios
		 * 	 
		 */
		return false;
	}

	private static boolean esCadena(String substring) {
		/* TODO Cadena
		 * debe diferenciar cuando es cadena(que tiene el caracter " al inicio y al final) o cuando es un error
		 */
		
		
		return false;
	}

	private static boolean esNumero(String substring) {
		/* TODO Numero
		 * Debe manejar errores
		 * Diferenciar entre doubles y enteros
		 */
		return false;
	}

	private static boolean esIdentificador(String substring) {
		/*  TODO Auto-generated method stub
		 *  Debe saber cuando es una palabra reservada y cuando es un identificador
		 *  Manejo de errores
		 */
		return true;
	}









	private static HashMap <String,String> operadoresEspeciales;
	private static int columna;
	private static int fila;
}
