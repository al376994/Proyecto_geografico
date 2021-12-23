package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;

import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class TestsAceptacion_H_2_2 {
	private Usuario usuario;
	private ListaUsuario listaUsuario;

	@BeforeEach
	public void setUp() throws SQLException {
		listaUsuario = new ListaUsuario();
		usuario = new Usuario();
		usuario.setNombre("userP");
		usuario.setContrasena("passP");
		limpiarBaseDeDatos();
		listaUsuario.addUsuario(usuario);
		// Esto, aunque redundante, sirve para simular el comportamiento completo del programa
		usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
		usuario.addAPIGeocode(new APIGeocode());
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
		}
	}

	@Test
	public void elegirAPIEspecificaParaUbicacion_E2_2_1_UbicacionTieneLaAPIEspecificada() throws SQLException, NotFoundPlaceException, AlreadyHasPlaceException {
		//Arrange
		Ubicacion castellon = usuario.altaUbicacionToponimo("Castellón");
		usuario.activarServicioAPI("AirVisual");
		//Act
		usuario.altaServicioUbicacion(castellon, "AirVisual");
		//Assert
		assertEquals(1, usuario.getServiciosAPIUbicacion(castellon).size());
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_2_3_noSeMuestraNingunaInformacion() throws SQLException, NotFoundPlaceException, AlreadyHasPlaceException {
		//Arrange
		Ubicacion castellon = usuario.altaUbicacionToponimo("Castellón");
		usuario.activarServicioAPI("AirVisual");
		usuario.darDeBajaUbicacion(castellon);
		//Assert										 //Act
		assertThrows(NotFoundPlaceException.class, () -> usuario.altaServicioUbicacion(castellon, "AirVisual"));
		assertTrue(usuario.getServiciosAPIUbicacion(castellon).isEmpty());
	}
}
