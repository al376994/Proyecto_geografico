package Geografico.Aceptacion;

import Geografico.model.*;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_7_2 {
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
    public void desactivarLocacalizacionFavorita_E7_2_1_desactivadoCorrecto() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);
        //Act
        usuario.toggleFavoritoUbicacion(castellon);
        boolean anadido = dataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertTrue(anadido);
    }

    @Test
    public void desactivarLocacalizacionFavorita_E7_2_2_desactivadoIncorrecto() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        Ubicacion ubicacion = usuario.altaUbicacionToponimo("Castellón");
        List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);
        //Act
        boolean anadido = dataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertFalse(anadido);
    }
}
