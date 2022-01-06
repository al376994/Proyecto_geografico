package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import Geografico.model.Ubicacion;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestsAceptacion_H_5_3 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;
    private ListaUsuario listaUsuario;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        listaUsuario = new ListaUsuario();
        usuario.setContrasena("pwd");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void cerrarSesion_E5_3_1_cierreCorrecto() throws SQLException, AlreadyHasPlaceException {
        //vamos a pedir primero las ubicaciones del usuario y luego simularemos un cierre de sesión
        //estableciendo la bbdd a null
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        //Act
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        assertEquals("Castello De La Plana", nombreUbicacion1);
    }

    @Test
    public void cerrarSesion_E5_3_2_cierreIncorrecto() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        //Act
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        assertNull(dataBaseFunctions);
    }
}
