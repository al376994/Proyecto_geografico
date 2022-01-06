package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_5_2 {
	private Usuario usuario;
	private DataBaseFunctions dataBaseFunctions;
	private ListaUsuario listaUsuario;

	@BeforeEach
	private void iniciarVariables() throws SQLException {
		usuario = new Usuario();
		usuario.setNombre("usuarioIS");
		dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
		listaUsuario = new ListaUsuario();
		limpiarBaseDeDatos();
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
		}
	}

	@Test
	public void iniciarSesion_E5_2_1_inicioCorrecto() throws SQLException {
		//Arrange
		usuario.setContrasena("pwd");
		//Act
		dataBaseFunctions.addUsuario(usuario);
		int i = dataBaseFunctions.iniciarSesion(usuario.getNombre(), usuario.getContrasena());
		//Assert
		assertEquals(2, i);
	}

	@Test
	public void iniciarSesion_E5_2_3_inicioIncorrecto() throws SQLException {
		//Arrange
		usuario.setContrasena("pwd");
		//Act
		dataBaseFunctions.addUsuario(usuario);
		int i = dataBaseFunctions.iniciarSesion(usuario.getNombre(), "");
		//Assert
		assertEquals(1, i);
	}
}
