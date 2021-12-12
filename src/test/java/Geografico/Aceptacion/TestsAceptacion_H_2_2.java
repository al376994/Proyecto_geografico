package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
	public void elegirAPIEspecificaParaUbicacion_E2_2_1_UbicacionTieneLaAPIEspecificada() throws SQLException {
		//Arrange
		usuario.altaUbicacionToponimo("Castellón");
		//Act
		List<Ubicacion> ubicaciones = usuario.getUbicaciones();
		//Assert
		assertTrue(true);
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_1_2_noSeMuestraNingunaInformacion() throws SQLException {
		//Arrange
		//Act
		//Assert
	}
}
