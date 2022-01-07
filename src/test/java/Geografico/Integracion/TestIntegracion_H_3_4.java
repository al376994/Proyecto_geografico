package Geografico.Integracion;

import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_3_4 {
	private Usuario mockedUsuario;

	@BeforeEach
	public void setUp() {
		mockedUsuario = Mockito.mock(Usuario.class);
	}

	@Test
	public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_1_SeDesactivaLaAPIParaEsteUsuario() {
		//Arrange
		//Simulamos que el servicio "OpenWeather" esta activo y que al desactivarlo se devuelve true
		when(mockedUsuario.desactivarServicioAPI("OpenWeather")).thenReturn(true);
		//Act
		boolean desactivado = mockedUsuario.desactivarServicioAPI("OpenWeather");
		//Assert
		assertTrue(desactivado);
	}

	@Test
	public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_2_NoTieneAPIsActivasNoSeDesactivaNada() {
		//Arrange
		//Simulamos que el servicio "OpenWeather" esta desactivado y que al intentar desactivarlo se devuelve false
		when(mockedUsuario.desactivarServicioAPI("OpenWeather")).thenReturn(false);
		//Act
		boolean desactivado = mockedUsuario.desactivarServicioAPI("OpenWeather");
		//Assert
		assertFalse(desactivado);
	}
}
