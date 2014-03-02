package model;

import org.junit.Assert;
import org.junit.Test;

import connection.parsers.Parser;


public class UsuarioTest {

	@Test
	public void convertUsuarioToXmlTest() {
		
		Usuario usuario = new Usuario();
		usuario.setActivado(false);
		usuario.setApellido("a");
		usuario.setEmail("jlara91@gmail.com");
		usuario.setHabilitado(true);
		usuario.setNombre("a");
		usuario.setId(117);

		Parser parser = new Parser();
		String xml = parser.convertToXml(usuario, Usuario.class);
		Assert.assertEquals("<Usuario><id>117</id><nombre>a</nombre><apellido>a</apellido><email>jlara91@gmail.com</email>" +
				"<activado>false</activado><habilitado>true</habilitado></Usuario>", xml);
	}
	
	@Test
	public void convertUsuarioWithOnlyUsernameToXmlTest() {
		
		Usuario usuario = new Usuario();
		usuario.setUsername("lala");

		Parser parser = new Parser();
		String xml = parser.convertToXml(usuario, Usuario.class);
		Assert.assertEquals("<Usuario><username>lala</username></Usuario>", xml);
	}
	
	@Test
	public void convertXmlToUsuarioTest() {
		
		String xml = "<Usuario><id>117</id><nombre>a</nombre><apellido>a</apellido><email>jlara91@gmail.com</email>" +
				"<activado>false</activado><habilitado>true</habilitado></Usuario>";
		
		Parser parser = new Parser();
		Usuario usuario = (Usuario)parser.unmarshal(xml, Usuario.class);
		
		Assert.assertEquals("a", usuario.getApellido());
		Assert.assertEquals(new Integer(117), usuario.getId());
		
	}
	
	@Test
	public void convertXmlToUsuarioWithOnlyUsernameTest() {
		
		String xml = "<Usuario><username>lala</username></Usuario>";
		
		Parser parser = new Parser();
		Usuario usuario = (Usuario)parser.unmarshal(xml, Usuario.class);
		
		Assert.assertEquals("lala", usuario.getUsername());
		
	}
	
	
	@Test
	public void convertXmlToUsuarioWithFullDataAndReturnsTest() {
		
		String xml;
		
		xml = "\n  <Usuario>\n    <id>102547</id>\n    <activado>true</activado>\n    <habilitado>true</habilitado>\n    <username>damian</username>\n  </Usuario>\n";
		
		Parser parser = new Parser();
		Usuario usuario = (Usuario)parser.unmarshal(xml, Usuario.class);
		
		Assert.assertEquals("damian", usuario.getUsername());
		Assert.assertEquals(new Integer(102547), usuario.getId());
		Assert.assertTrue(usuario.getActivado());
		Assert.assertTrue(usuario.getHabilitado());
		
	}
	
}
