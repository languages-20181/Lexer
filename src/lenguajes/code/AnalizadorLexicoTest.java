/**
 *
 */
package lenguajes.code;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		AnalizadorLexico analizador = new AnalizadorLexico("prueba.txt");
		return;
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

	@Test
	void testCadenas() {
		assertEquals(true, AnalizadorLexico.esCadena("\"abc\""));
		assertEquals(false, AnalizadorLexico.esCadena("abc\""));
		assertEquals(false, AnalizadorLexico.esCadena("\"abc"));
		assertEquals(false, AnalizadorLexico.esCadena("abc"));
	}


	@Test
	void testDecimal() {
		assertEquals(true, AnalizadorLexico.esDecimal("1.2"));
		assertEquals(true, AnalizadorLexico.esDecimal("-12.5"));
		assertEquals(false, AnalizadorLexico.esDecimal("0.545."));
		assertEquals(false, AnalizadorLexico.esDecimal("..5548"));
	}

	@Test
    void testEnteros(){
        assertEquals(true, AnalizadorLexico.esEntero("1"));
        assertEquals(true, AnalizadorLexico.esEntero("0"));
        assertEquals(false, AnalizadorLexico.esEntero("1.2"));
        assertEquals(true, AnalizadorLexico.esEntero("-54"));
        assertEquals(false, AnalizadorLexico.esEntero("-4.5"));
        assertEquals(false, AnalizadorLexico.esEntero("1.0"));
    }

    @Test
    void testComentarios(){
        assertEquals(true, AnalizadorLexico.esComentario("#Comment"));
        assertEquals(true, AnalizadorLexico.esComentario("#"));
        assertEquals(false, AnalizadorLexico.esComentario("a#"));
        assertEquals(false, AnalizadorLexico.esComentario("7#"));
        assertEquals(true, AnalizadorLexico.esComentario("#######"));
    }

    @Test
    void testIdentificador(){
        assertEquals("log", AnalizadorLexico.esIdentificador("log"));
        assertEquals("if", AnalizadorLexico.esIdentificador("if"));
        assertEquals("importar", AnalizadorLexico.esIdentificador("importar"));
        assertEquals("id", AnalizadorLexico.esIdentificador("lo"));
        assertEquals("id", AnalizadorLexico.esIdentificador("a"));
        assertEquals("id", AnalizadorLexico.esIdentificador("f4544aA"));
        assertEquals("0", AnalizadorLexico.esIdentificador("1d_auth_ms"));
        assertEquals("0", AnalizadorLexico.esIdentificador("abc#fbi"));
    }
}