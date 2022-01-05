package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_2_3 {
    private Usuario usuario;
    private Ubicacion ubicacion;
    private ListaUsuario listaUsuario;

    @BeforeEach
    public void setUp() throws SQLException, AlreadyHasPlaceException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("userP");
        usuario.setContrasena("passP");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
        usuario.addAPIGeocode(new APIGeocode());
        ubicacion = usuario.altaUbicacionToponimo("Castellón");
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void consultarListaUbicacionesActivas_E2_3_1_seMuestraLaLista() throws SQLException {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicacionesActivas();
        //Assert
        assertEquals(1, ubicacionesActivas.size());
    }

    @Test
    public void consultarListaUbicacionesActivas_E2_3_2_noHayUbicacionesEnLaLista() throws SQLException {
        //Arrange
        usuario.darDeBajaUbicacion(ubicacion);
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicacionesActivas();
        //Assert
        assertEquals(0, ubicacionesActivas.size());
    }
}
