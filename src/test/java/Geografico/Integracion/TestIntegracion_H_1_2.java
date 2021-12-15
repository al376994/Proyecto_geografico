package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_1_2 {
	private APIGeocodeInterface mockedApiGeocode;
	private ListaUsuario mockedListaUsuario;
	private Usuario usuario;
	private DataBaseFunctions mockedDataBaseFunctions;

	@BeforeEach
	void setUp() throws SQLException {
		mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
		mockedListaUsuario = Mockito.mock(ListaUsuario.class);
		mockedDataBaseFunctions = Mockito.mock((DataBaseFunctions.class));
		when(mockedListaUsuario.getUsuario("Nombre1", null)).thenReturn(new Usuario("Nombre1"));
		usuario = mockedListaUsuario.getUsuario("Nombre1", null);
		usuario.setDataBaseFunctions(mockedDataBaseFunctions);
	}


	@Test
	public void altaUbicacionCoordenadas_E1_2_1_seAñadeLista() throws SQLException, CoordenadasExcepcion, AlreadyHasPlaceException {
		//Arrange
		usuario.addAPIGeocode(mockedApiGeocode);
		//Act
		ArrayList lista = new ArrayList();
		lista.add(new Ubicacion());
		when(mockedDataBaseFunctions.listarUbicacionesUsuario("Nombre1")).thenReturn(lista);
		when(mockedApiGeocode.getUbicacionCoordenadas(39.986,-0.0376709)).thenReturn(new Ubicacion());
		usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(1, listaUbicaciones.size());
	}

	@Test
	public void altaUbicacionToponimo_E1_2_2_listaUbicacionesVacia() throws SQLException, CoordenadasExcepcion, AlreadyHasPlaceException {
		//Arrange
		usuario.addAPIGeocode(mockedApiGeocode);
		//Act
		ArrayList lista = new ArrayList();
		when(mockedDataBaseFunctions.listarUbicacionesUsuario("Nombre1")).thenReturn(lista);
		when(mockedApiGeocode.getUbicacionCoordenadas(100,-100)).thenReturn(null);
		usuario.altaUbicacionCoordenadas(100,-100);
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(0, listaUbicaciones.size());
	}
}

