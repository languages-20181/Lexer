package lenguajes.code;

import java.io.IOException;

public class App {

    public static final String textFile = "prueba.txt";

	public static void main(String[] args) throws IOException {
        analizador = new AnalizadorLexico(textFile);
        //analizador.entrada(textFile);
    }
	
	private static AnalizadorLexico analizador;

}
