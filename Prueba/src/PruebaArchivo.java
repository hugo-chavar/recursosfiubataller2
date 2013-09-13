import static org.junit.Assert.*;

import org.junit.Test;
import Archivos.*;

public class PruebaArchivo {

	
	public Archivo inicializar(){
		Archivo unArchivo = new Archivo();
		return unArchivo;
	}
	@Test
	public void testArchivoInicial() {
		Archivo unArchivo = inicializar();
		
		assertEquals("El archivo se crea sin link", unArchivo.getLink(), "");
	}
	@Test
	public void testArchivoAgregoLink() {
		Archivo unArchivo = inicializar();
		
		unArchivo.setLink("casa/tp.pdf");
		assertEquals("Seteo un link y lo toma bien", unArchivo.getLink(), "casa/tp.pdf");
	}
}
