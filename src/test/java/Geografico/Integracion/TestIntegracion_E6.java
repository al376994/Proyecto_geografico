package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class TestIntegracion_E6 {
	private APIGeocodeInterface mockedApiGeocode;

	@BeforeEach
	void setUp(){
		mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
	}

	@Test
	public void obtenerCoordenadasDeToponimo_E1_6_1_devuelveCoordenadasCastellon(){
		//Arrange
		Coordenadas coordenadasCastellon = new Coordenadas(39.97990, -0.03304);
		//Act
		Ubicacion castellonFalso = new Ubicacion();
		castellonFalso.setCoordenadas(coordenadasCastellon);
		when(mockedApiGeocode.getCoordenadasDeToponimo("Castellón")).thenReturn(castellonFalso.getCoordenadas());
		Coordenadas coordenadasCastellonPorPeticion = mockedApiGeocode.getCoordenadasDeToponimo("Castellón");
		//Assert
		assertEquals(coordenadasCastellon, coordenadasCastellonPorPeticion);
	}

	@Test
	public void obtenerCoordenadasDeToponimo_E1_6_2_devuelveNull(){
		//Arrange
		//Act
		when(mockedApiGeocode.getCoordenadasDeToponimo(";")).thenReturn(null);
		Coordenadas coordenadasCastellonPorPeticion = mockedApiGeocode.getCoordenadasDeToponimo(";");
		//Assert
		assertNull(coordenadasCastellonPorPeticion);
	}
}
