package Geografico.Integracion;

import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_2_1 {
	private Usuario mockedUsuario;
	private ListaUsuario mockedListaUsuario;

	@BeforeEach
	public void setUp() throws SQLException {
		mockedListaUsuario = Mockito.mock(ListaUsuario.class);
		mockedUsuario = Mockito.mock(Usuario.class);
		mockedUsuario.setNombre("UserP");
		mockedUsuario.setContrasena("PassP");
		when(mockedListaUsuario.getUsuario("UserP", "PassP")).thenReturn(mockedUsuario);
		mockedUsuario = mockedListaUsuario.getUsuario("UserP", "PassP");
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_1_1_seMuestraLaInformacion() throws SQLException {
		//Arrange

		int vecesInfoConsultada = 0;
		Ubicacion ubicacion = new Ubicacion();
		//Act
		when(mockedUsuario.getUbicaciones()).thenReturn(List.of(ubicacion, ubicacion, ubicacion));
		List<Ubicacion> ubicaciones = mockedUsuario.getUbicaciones();
		for (Ubicacion _ubicacion: ubicaciones) {
			vecesInfoConsultada += 1;
			_ubicacion.getInformacion();
		}
		//Assert
		assertTrue(vecesInfoConsultada >= 3);
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_1_2_noSeMuestraNingunaInformacion() throws SQLException {
		//Arrange
		int vecesInfoConsultada = 0;
		//Act
		when(mockedUsuario.getUbicaciones()).thenReturn(new ArrayList<Ubicacion>());
		List<Ubicacion> ubicaciones = mockedUsuario.getUbicaciones();
		for (Ubicacion ubicacion: ubicaciones) {
			vecesInfoConsultada += 1;
			ubicacion.getInformacion();
		}
		//Assert
		assertEquals(0, vecesInfoConsultada);

	}
}
