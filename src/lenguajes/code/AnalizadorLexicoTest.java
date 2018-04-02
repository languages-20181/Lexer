/**
 * 
 */
package lenguajes.code;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author giovanny
 *
 */
class AnalizadorLexicoTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		AnalizadorLexico analizador = new AnalizadorLexico("/home/giovanny/Desktop/prueba.txt");
	}
	@Test
	void testSOperadoresEspeciales() {
		assertEquals("hola", AnalizadorLexico.manejadorTexto("hola"));
		assertEquals("ala", AnalizadorLexico.manejadorTexto("hola}ala"));
		assertEquals("", AnalizadorLexico.manejadorTexto("hola}"));
		assertEquals("", AnalizadorLexico.manejadorTexto("}["));
	}
	
	@Test
	void testDosOperadoresEspeciales() {
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable>=otravariable"));
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable==otravariable"));
		assertEquals("otravariable", AnalizadorLexico.manejadorTexto("variable!=otravariable"));
		
	}
}
