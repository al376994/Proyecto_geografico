package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestsAceptacion_E1_2 {
	private APIGeocodeInterface apiGeocode;
	private ListaUsuario listaUsuario;
	private Usuario usuario;

	//mejorar nombre
	@BeforeEach
	private void iniciarVariables() throws SQLException {
		apiGeocode = new APIGeocode();
		listaUsuario = new ListaUsuario();
		usuario = new Usuario();			//para hacer tests al principio creamos un usuario a mano
		usuario.setNombre("usuario5");
		listaUsuario.addUsuario(usuario);	// No hace nada de momento, cuando la comunicacion con la base de
											// datos este implmentada se usara y podremos descomentar el siguiente
											// trozo de codigo también
		//usuario = listaUsuario.getUsuario("Nombre1");	// esto es solo para mostrar como se conseguiria
														// el usuario realmente
	}

	@Test
	public void altaUbicacionToponimo_E1_1_1_seAñadeLista() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionToponimo("Castellón");
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(1, listaUbicaciones.size());
	}

	@Test
	public void altaUbicacionToponimo_E1_1_2_listaUbicacionesVacia() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionToponimo(";");
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(0, listaUbicaciones.size());
	}

	@Test
	public void altaUbicacionCoordenadas_E1_2_1_seAñadeLista() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(1, listaUbicaciones.size());
	}

	@Test
	public void altaUbicacionCoordenadas_E1_2_3_listaUbicacionesVacia() throws SQLException {
		//Arrange
		usuario.addAPIGeocode(apiGeocode);
		//Act
		usuario.altaUbicacionCoordenadas(100, -100);
		//Assert
		List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
		assertEquals(0, listaUbicaciones.size());
	}
}
