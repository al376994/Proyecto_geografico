package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_E3 {
	private APIGeocodeInterface mockedApiGeocode;

	@BeforeEach
	void setUp(){
		mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
	}

	@Test
	public void validarToponimo_E1_3_1_devuelveTrue(){
		//Arrange
		//Act
		when(mockedApiGeocode.validarToponimo("Castellón")).thenReturn(true);
		boolean valido = mockedApiGeocode.validarToponimo("Castellón");
		//Assert
		assertTrue(valido);
	}

	@Test
	public void validarToponimo_E1_3_2_devuelveFalse(){
		//Arrange
		//Act
		when(mockedApiGeocode.validarToponimo(";")).thenReturn(false);
		boolean valido = mockedApiGeocode.validarToponimo(";");
		//Assert
		//salta excepcion
		assertFalse(valido);
	}
}
