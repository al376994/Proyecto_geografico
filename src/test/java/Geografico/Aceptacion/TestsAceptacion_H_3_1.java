package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestsAceptacion_H_3_1 {
	DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

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
		dataBaseFunctions.addServicioAPIDisponible("ServicioImaginario");
	}

	@AfterEach
	private void limpiarBaseDeDatos() throws SQLException {
		if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
			listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
		}
		dataBaseFunctions.deleteServicioAPIDisponible("ServicioImaginario");
	}

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_1_SeMuestranLosDisponibles() {
        //Arrange
        //Act
		boolean tieneServicio = usuario.getServiciosAPIDisponibles().contains("ServicioImaginario");
        //Assert
		assertTrue(tieneServicio);
    }

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_2_SeNotificaQueNoHayDisponibles() {
        //Arrange
		dataBaseFunctions.deleteServicioAPIDisponible("ServicioImaginario");
        //Act
		boolean tieneServicio = usuario.getServiciosAPIDisponibles().contains("ServicioImaginario");
		//Assert
		assertFalse(tieneServicio);
    }
}
