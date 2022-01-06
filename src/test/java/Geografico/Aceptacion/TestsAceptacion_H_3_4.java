package Geografico.Aceptacion;

import Geografico.model.*;
import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_3_4 {
	private Usuario usuario;
	private ListaUsuario listaUsuario;

	@BeforeEach
	public void setUp() throws SQLException {
		listaUsuario = new ListaUsuario();
		usuario = new Usuario();
		usuario.setNombre("userP");
		usuario.setContrasena("passP");
		limpiarBaseDeDatos();
		listaUsuario.addUsuario(usuario);
		// Esto, aunque redundante, sirve para simular el comportamiento completo del programa
		usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
		usuario.addAPIGeocode(new APIGeocode());
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
		}
	}

	@Test
	public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_1_SeDesactivaLaAPIParaEsteUsuario() {
		//Arrange
		usuario.activarServicioAPI(APIHelper.WEATHERAPI);
		//Act
		boolean desactivado = usuario.desactivarServicioAPI(APIHelper.WEATHERAPI);
		//Assert
		assertTrue(desactivado);
	}

	@Test
	public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_2_NoTieneAPIsActivasNoSeDesactivaNada() {
		//Arrange
		//Act
		boolean desactivado = usuario.desactivarServicioAPI(APIHelper.WEATHERAPI);
		//Assert
		assertFalse(desactivado);
	}
}
