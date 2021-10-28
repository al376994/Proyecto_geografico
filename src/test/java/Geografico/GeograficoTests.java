package Geografico;

import Geografico.model.APILocalizador;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class GeograficoTests {
	@Mock
	private APILocalizador localizadorMock;

	@Test /* Esto realmente no es un buen uso de mock porque no se prueba nada, pero es un ejemplo para ver como va */
	void ExisteUbicacionPorToponimo_escValido_resTrue() {
		//arrange
		when(localizadorMock.existsUbicationByToponimo("España")).thenReturn(true);
		//act
		boolean doesSpainExists = localizadorMock.existsUbicationByToponimo("España");
		//assert
		assertTrue(doesSpainExists);
	}

}
