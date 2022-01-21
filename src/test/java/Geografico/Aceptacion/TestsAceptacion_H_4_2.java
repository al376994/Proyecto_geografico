package Geografico.Aceptacion;

import Geografico.model.*;
import Geografico.model.API.APIGeocode;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestsAceptacion_H_4_2 {
    private Usuario usuario;
    private ListaUsuario listaUsuario;
    private DataBaseFunctions dataBaseFunctions;

    @BeforeEach
    public void setUp() throws SQLException, AlreadyHasPlaceException {
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
        usuario.setContrasena("pass");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
        usuario.addAPIGeocode(new APIGeocode());
        usuario.altaUbicacionToponimo("Montanejos");
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void recuperarContenidoCierreInesperado_E4_2_1_seRecupera() throws SQLException {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        List<Ubicacion> ubicacionesActiva1 = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion2 = ubicacionesActiva1.get(0).getNombre();
        //Assert
        assertEquals(nombreUbicacion2, nombreUbicacion1);
    }

    @Test
    public void recuperarContenidoCierreInesperado_E4_2_2_noSeRecupera() throws SQLException {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        String nombreUbicacion2;
        try {
            nombreUbicacion2 = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre()).get(0).getNombre();
        } catch (NullPointerException e) {
            nombreUbicacion2 = null;
        }
        //Assert
        assertNull(nombreUbicacion2);
    }
}
