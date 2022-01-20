package Geografico.Aceptacion;

import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIAirVisualInterface;
import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TestsAceptacion_H_1_3 {
	private APIGeocodeInterface apiGeocode;

	@BeforeEach
	public void iniciarVariables(){
		apiGeocode = new APIGeocode();
	}

	@Test
	public void validarToponimo_E1_3_1_devuelveTrue(){
		//Arrange
		//Act
		boolean valido = apiGeocode.validarToponimo("Castellón");
		//Assert
		assertTrue(valido);
	}

	@Test
	public void validarToponimo_E1_3_2_devuelveFalse(){
		//Arrange
		//Act
		boolean valido = apiGeocode.validarToponimo(";");
		//Assert
		assertFalse(valido);
	}
}
