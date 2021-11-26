package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.Coordenadas;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestsAceptacion_H_1_6 {
	private APIGeocodeInterface apiGeocode;

	@BeforeEach
	public void iniciarVariables(){
		apiGeocode = new APIGeocode();
	}

	@Test
	public void obtenerCoordenadasDeToponimo_E1_6_1_devuelveCoordenadasCastellon(){
		//Arrange
		Coordenadas coordenadasCastellon = new Coordenadas(39.97990, -0.03304);
		//Act
		Coordenadas coordenadasCastellonPorPeticion = apiGeocode.getCoordenadasDeToponimo("Castellón");
		//Assert
		assertEquals(coordenadasCastellon, coordenadasCastellonPorPeticion);
	}

	@Test
	public void obtenerCoordenadasDeToponimo_E1_6_2_devuelveNull(){
		//Arrange
		//Act
		Coordenadas coordenadasCastellonPorPeticion = apiGeocode.getCoordenadasDeToponimo(";");
		//Assert
		assertNull(coordenadasCastellonPorPeticion);
	}
}
