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

	@Test
	void ExisteUbicacionPorToponimo_escValido_resTrue() {
		//arrange
		when(localizadorMock.existsUbicationByToponimo("España")).thenReturn(true);
		//act
		boolean doesSpainExists = localizadorMock.existsUbicationByToponimo("España");
		//assert
		assertTrue(doesSpainExists);
	}

}
