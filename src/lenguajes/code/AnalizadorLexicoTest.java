/**
 * 
 *//*

package lenguajes.code;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

*/
/**
 * @author giovanny
 *
 *//*

class AnalizadorLexicoTest {
	
	*/
/**
	 * @throws java.lang.Exception
	 *//*

	@BeforeEach
	void setUp() throws Exception {
		AnalizadorLexico analizador = new AnalizadorLexico("/home/giovanny/Desktop/prueba.txt");
	}
	
//	@Test
//	void testSOperadoresEspeciales() {
//		assertEquals("hola", AnalizadorLexico.manejadorTexto("hola"));
//		assertEquals("ala", AnalizadorLexico.manejadorTexto("hola}ala"));
//		assertEquals("", AnalizadorLexico.manejadorTexto("hola}"));
//		assertEquals("", AnalizadorLexico.manejadorTexto("}["));
//	}
	
	@Test
	void testDosOperadoresEspeciales() {
		String test ="otra"
				+ "variable";
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable >=" +test));
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable==otravariable"));
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable!=otravariable"));
		
	}

	@Test
	void testCadenas() {
		assertEquals(true, AnalizadorLexico.esCadena("\"abc\""));
		//assertEquals(false, AnalizadorLexico.esCadena("abc\""));
		//assertEquals(false, AnalizadorLexico.esCadena("\"abc"));
		//assertEquals(false, AnalizadorLexico.esCadena("abc"));
	}
	@Test
	void testComentario() {
		assertEquals(true, AnalizadorLexico.esComentario("#hola"));
		assertEquals(false, AnalizadorLexico.esComentario("h#ola"));
	}
}
*/
