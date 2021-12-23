package Geografico.Integracion;

import Geografico.model.API.APIAirVisual;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_2_2 {
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
	public void elegirAPIEspecificaParaUbicacion_E2_2_1_UbicacionTieneLaAPIEspecificada() throws SQLException, NotFoundPlaceException, AlreadyHasPlaceException {
		//Arrange
		when(mockedUsuario.altaUbicacionToponimo("Castellón")).thenReturn(new Ubicacion("Castellón"));
		Ubicacion castellon = mockedUsuario.altaUbicacionToponimo("Castellón");
		mockedUsuario.activarServicioAPI("AirVisual");
		//Act
		mockedUsuario.altaServicioUbicacion(castellon, "AirVisual");
		when(mockedUsuario.getServiciosAPIUbicacion(castellon)).thenReturn(List.<String>of("AirVisual"));
		//Assert
		assertEquals(1, mockedUsuario.getServiciosAPIUbicacion(castellon).size());
	}

	@Test
	public void consultarInformacionUbicacionesActivas_E2_2_3_noSeMuestraNingunaInformacion() throws SQLException, NotFoundPlaceException, AlreadyHasPlaceException {
		//Arrange
		when(mockedUsuario.altaUbicacionToponimo("Castellón")).thenReturn(new Ubicacion("Castellón"));
		Ubicacion castellon = mockedUsuario.altaUbicacionToponimo("Castellón");
		mockedUsuario.activarServicioAPI("AirVisual");
		mockedUsuario.darDeBajaUbicacion(castellon);
		//Assert										 //Act
		when(mockedUsuario.altaServicioUbicacion(castellon, "AirVisual")).thenThrow(NotFoundPlaceException.class);
		when(mockedUsuario.getServiciosAPIUbicacion(castellon)).thenReturn(new ArrayList<String>());
		assertThrows(NotFoundPlaceException.class, () -> mockedUsuario.altaServicioUbicacion(castellon, "AirVisual"));
		assertTrue(mockedUsuario.getServiciosAPIUbicacion(castellon).isEmpty());
	}
}
