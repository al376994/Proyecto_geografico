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
public class TestsAceptacion_E3 {
	//private APIAirVisualInterface apiAirVisual;
	private APIGeocodeInterface apiGeocode;

	@BeforeEach
	public void iniciarVariables(){
		//apiAirVisual = new APIAirVisual();
		apiGeocode = new APIGeocode();
	}

	@Test
	public void validarToponimo_E1_3_1_devuelveTrue(){
		//Arrange
		//Act
		boolean valido = apiGeocode.validarToponimo("Castellón");
		//Boolean valido = apiAirVisual.validarToponimo("Castellón");
		//esto de aqui debajo lo hace valido si se descomenta y tecnicamente funciona como debería
		//valido = apiAirVisual.validarToponimoExaustivo("Castello%20de%20la%20Plana", "Valencia", "Spain");
		//Assert
		assertTrue(valido);
	}

	@Test
	public void validarToponimo_E1_3_1_devuelveFalse(){
		//Arrange
		//Act
		boolean valido = apiGeocode.validarToponimo(";");
		//Boolean valido = apiAirVisual.validarToponimo(";");
		//Assert
		//salta excepcion
		assertFalse(valido);
	}
}
