package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_4_1 {
    private Usuario usuario;
    private ListaUsuario listaUsuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp() throws SQLException, AlreadyHasPlaceException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
        usuario.setContrasena("pass");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
        usuario.addAPIGeocode(new APIGeocode());
        ubicacion = usuario.altaUbicacionToponimo("Montanejos");
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void recuperarContenido_E4_1_1_seRecupera() throws SQLException {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicaciones();
        String nombreUbicacion = ubicacionesActivas.get(0).getNombre();
        //Assert
        assertEquals(ubicacion.getNombre(), nombreUbicacion);
    }

    @Test
    public void recuperarContenido_E4_1_2_noSeRecupera() throws SQLException {
        //Arrange
        Usuario usuarioNoGuardado = new Usuario();
        usuarioNoGuardado.setNombre("usuarioNoGuardado");
        //Act
        List<Ubicacion> ubicacionesActivas = usuarioNoGuardado.getUbicaciones();
        //Assert
        assertEquals(0, ubicacionesActivas.size());
    }
}
