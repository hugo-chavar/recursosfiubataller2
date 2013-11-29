package com.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import ar.fiuba.redsocedu.datalayer.ws.Usuario;

import com.utils.NotificacionFactory;
import com.ws.handler.UsuarioHandler;
import com.ws.parsers.UsuarioParser;
import com.ws.serializers.NotificacionSerializer;
import com.ws.services.IntegracionWS;

public class UsuarioTestIntegration {

	Usuario usuario1, usuario2;

	@Before
	public void setUp() throws Exception {
		IntegracionWS integracionWS = new IntegracionWS();
		String xmlUser1 = "<?xml version=\"1.0\"?><WS><Usuario><username>usuario_prueba1</username><password>1234</password><activado>true</activado><habilitado>true</habilitado></Usuario></WS>";

		guardarDatos(xmlUser1, integracionWS);

		String nuevoXml1 = consultarDatos(xmlUser1, integracionWS);
		usuario1 =obtenerUsuario(nuevoXml1, integracionWS);
	}
	
	@After
	public void cleanUp() throws Exception {
		IntegracionWS integracionWS = new IntegracionWS();
		String prefix = "<?xml version=\"1.0\"?><WS><Usuario><id>";
		String suffix = "</id></Usuario></WS>";
		String xmlUser1 = createDeleteUserXML(prefix, suffix, usuario1);
		integracionWS.eliminarDatos(xmlUser1);
		
	}

	private Usuario obtenerUsuario(String xml, IntegracionWS integracionWS) throws SAXException, IOException, ParserConfigurationException {
		xml = xml.replace("\n", "");
		xml = xml.replace(" ", "");
		UsuarioHandler handler = new UsuarioHandler();
		UsuarioParser parser1 = new UsuarioParser(integracionWS.getXMLDocument(xml));
		return handler.toDatabaseUser(parser1.getEntidadUsuario());
	}

	private void guardarDatos(String xml, IntegracionWS integracionWS) {
		if (!integracionWS.guardarDatos(xml).equals(NotificacionSerializer.getXMLfromPojo(NotificacionFactory.Exito()))) {
			Assert.fail("no se pudieron guardar los datos de: " + xml);
		}
	}

	private String consultarDatos(String xml, IntegracionWS integracionWS) {
		String usuarios = integracionWS.seleccionarDatos(xml);
		if (usuarios == null || usuarios == "") {
			Assert.fail("no se pudieron obtener los datos de: " + xml);
		}
		return usuarios;
	}


	private String createDeleteUserXML(String preffix, String suffix,
			Usuario user) {
		return preffix + user.getUsuarioId().toString() + suffix;
	}

	@Test
	public void removeTest() {
		IntegracionWS integracionWS = new IntegracionWS();
		String xml1 = createCreateUserXML(usuario1);
		if (!integracionWS.guardarDatos(xml1).equals(
				NotificacionSerializer.getXMLfromPojo(NotificacionFactory
						.Exito()))) {
			Assert.fail("no se pudo guardar el usuario: " + xml1);
		}
		Assert.assertEquals(NotificacionSerializer.getXMLfromPojo(NotificacionFactory.Exito()), integracionWS.eliminarDatos(xml1));
	}

	@Test
	public void selectOneUserTest() {
		IntegracionWS integracionWS = new IntegracionWS();
		String xml = createSelectUserXML(usuario1);
		integracionWS.seleccionarDatos(xml);
	}

	private String createSelectUserXML(Usuario usuario) {
		return "<?xml version=\"1.0\"?><WS><Usuario><username>"
				+ usuario.getUsername() + "</username><password>"
				+ usuario.getPassword() + "</password><activado>"
				+ usuario.isActivado().toString() + "</activado><habilitado>"
				+ usuario.isHabilitado().toString()
				+ "</habilitado></Usuario></WS>";
	}

	@Test
	public void saveOneUserTest() {
		IntegracionWS integracionWS = new IntegracionWS();
		String xml = createCreateUserXML(usuario1);
		integracionWS.guardarDatos(xml);
	}

	private String createCreateUserXML(Usuario usuario) {
		return "<?xml version=\"1.0\"?><WS><Usuario><username>"
				+ usuario.getUsername() + "</username><password>"
				+ usuario.getPassword() + "</password><activado>"
				+ usuario.isActivado().toString() + "</activado><habilitado>"
				+ usuario.isHabilitado().toString()
				+ "</habilitado></Usuario></WS>";
	}

	@Test
	public void updateOneUserTest() {
		IntegracionWS integracionWS = new IntegracionWS();
		String xml = createUpdateUserXML(usuario1);
		integracionWS.actualizarDatos(xml);
	}

	private String createUpdateUserXML(Usuario usuario) {
		if (usuario.getUsuarioId() != null) {
			return "<?xml version=\"1.0\"?><WS><Usuario><id>"
					+ usuario.getUsuarioId().toString() + "</id><username>"
					+ usuario.getUsername() + "</username><password>"
					+ usuario.getPassword() + "</password><activado>"
					+ usuario.isActivado() + "</activado><habilitado>"
					+ usuario.isHabilitado() + "</habilitado></Usuario></WS>";
		} else {
			return "<?xml version=\"1.0\"?><WS><Usuario><username>"
					+ usuario.getUsername() + "</username><password>"
					+ usuario.getPassword() + "</password><activado>"
					+ usuario.isActivado() + "</activado><habilitado>"
					+ usuario.isHabilitado() + "</habilitado></Usuario></WS>";
		}

	}

}
