package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestsAceptacion_H_1_1 {
	private APIGeocodeInterface apiGeocode;
	private ListaUsuario listaUsuario;
	private Usuario usuario;

	//mejorar nombre
	@BeforeEach
	private void iniciarVariables() throws SQLException {
		apiGeocode = new APIGeocode();
		listaUsuario = new ListaUsuario();
		usuario = new Usuario();
		usuario.setNombre("usuarioPruebas");
		limpiarBaseDeDatos();
		listaUsuario.addUsuario(usuario);
		// Esto, aunque redundante, sirve para simular el comportamiento completo del programa
		usuario = listaUsuario.getUsuario(usuario.getNombre(), null);
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), null) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), null);
		}
	}

	@Test
	public void altaUbicacionToponimo_E1_1_1_seAñadeLista() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionToponimo("Castellón");
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(1, listaUbicaciones.size());
	}

	@Test
	public void altaUbicacionToponimo_E1_1_2_listaUbicacionesVacia() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionToponimo(";");
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(0, listaUbicaciones.size());
	}
}
