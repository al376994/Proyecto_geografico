package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_1_4 {
	private APIGeocodeInterface mockedApiGeocode;

	@BeforeEach
	void setUp(){
		mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
	}



	@Test
	public void validarCoordenadas_E1_4_1_devuelveTrue(){
		//Arrange
		//Act
		when(mockedApiGeocode.validarCoordenadas(39.986, -0.0376709)).thenReturn(true);
		boolean valido = mockedApiGeocode.validarCoordenadas(39.986, -0.0376709);
		//Assert
		assertTrue(valido);
	}

	@Test
	public void validarCoordenadas_E1_4_2_devuelveFalse(){
		//Arrange
		//Act
		when(mockedApiGeocode.validarCoordenadas(100, -100)).thenReturn(false);
		boolean valido = mockedApiGeocode.validarCoordenadas(100, -100);
		//Assert
		assertFalse(valido);
	}
}
