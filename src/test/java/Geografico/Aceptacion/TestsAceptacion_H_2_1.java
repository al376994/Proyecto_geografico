package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
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


public class TestsAceptacion_H_2_1 {
	private Usuario usuario;
	private ListaUsuario listaUsuario;

	@BeforeEach
	public void setUp() throws SQLException {
		listaUsuario = new ListaUsuario();
		usuario = new Usuario();
		usuario.setNombre("usuarioPruebas");
		limpiarBaseDeDatos();
		listaUsuario.addUsuario(usuario);
		// Esto, aunque redundante, sirve para simular el comportamiento completo del programa
		usuario = listaUsuario.getUsuario(usuario.getNombre(), null);
		usuario.addAPIGeocode(new APIGeocode());
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), null) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), null);
		}
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_1_1_seMuestraLaInformacion() throws SQLException {
		//Arrange
		int vecesInfoConsultada = 0;
		usuario.altaUbicacionToponimo("Castellón");
		usuario.altaUbicacionToponimo("Valencia");
		usuario.altaUbicacionToponimo("Alicante");
		//Act
		List<Ubicacion> ubicaciones = usuario.getUbicaciones();
		for (Ubicacion ubicacion: ubicaciones) {
			vecesInfoConsultada += 1;
			ubicacion.getInformacion();
		}
		//Assert
		assertTrue(vecesInfoConsultada >= 3);
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_1_2_noSeMuestraNingunaInformacion() throws SQLException {
		//Arrange
		int vecesInfoConsultada = 0;
		//Act
		List<Ubicacion> ubicaciones = usuario.getUbicaciones();
		for (Ubicacion ubicacion: ubicaciones) {
			vecesInfoConsultada += 1;
			ubicacion.getInformacion();
		}
		//Assert
		assertEquals(0, vecesInfoConsultada);

	}
}
