package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestsAceptacion_H_2_1 {
	private Usuario usuario;

	@BeforeEach
	public void setUp() {
		usuario = new Usuario();
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