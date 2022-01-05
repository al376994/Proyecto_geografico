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

public class TestsAceptacion_H_1_9 {
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
    public void desactivarUbicacionActiva_E1_9_1_laUbicacionQuedaDesactivada() {
        //Arrange
        //Las ubicaciones estan activadas por defecto
        //Act
        boolean desactivada = !usuario.desactivarUbicacion(ubicacion);
        //Assert
        assertTrue(desactivada);
    }

    @Test
    public void desactivarUbicacionYaDesactivada_E1_9_2_laUbicacionPermaneceDesactivada() {
        //Arrange
        usuario.desactivarUbicacion(ubicacion);
        //Act
        boolean desactivada = !usuario.desactivarUbicacion(ubicacion);
        //Assert
        assertTrue(desactivada);
    }
}
