package Geografico.Aceptacion;

import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_1_10 {
    private Usuario usuario;
    private Ubicacion ubicacion;
    private ListaUsuario listaUsuario;

    @BeforeEach
    private void setUp() throws SQLException, AlreadyHasPlaceException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("usuarioPruebas");
        usuario.setContrasena("pwd");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        usuario = listaUsuario.getUsuario(usuario.getNombre(), null);
        ubicacion = usuario.altaUbicacionToponimo("Castellón");
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), null) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), null);
        }
    }

    @Test
    public void darDeBajaUbicacionDisponible_E1_10_1_laUbicacionSeDaDeBaja() {
        //Arrange
        //Act
        Boolean baja = usuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertTrue(baja);
    }

    @Test
    public void darDeBajaUbicacionNoDisponible_E1_10_2_laUbicacionNoSePuedeDarDeBaja() {
        //Arrange
        usuario.darDeBajaUbicacion(ubicacion);
        //Act
        Boolean baja = usuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertFalse(baja);
    }
}
