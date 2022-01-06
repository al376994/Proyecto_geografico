package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_2_5 {
    private Usuario usuario;
    private ListaUsuario listaUsuario;

    @BeforeEach
    public void setUp() throws SQLException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("usuarioPruebas");
        usuario.setContrasena("pass");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena());
        usuario.addAPIGeocode(new APIGeocode());
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void reactivarUbicacionInactivaDelHistorial_E2_5_1_QuedaReactivada() throws AlreadyHasPlaceException {
        //Arrange
        Ubicacion ubicacionABorrar = usuario.altaUbicacionToponimo("Castellón");
        usuario.darDeBajaUbicacion(ubicacionABorrar);
        //Act
        List<Ubicacion> historialUbicaciones = usuario.getHistorialUbicaciones();
        boolean reactivada = usuario.reactivarUbicacionDelHistorial(ubicacionABorrar.getNombre());
        //Assert
        assertTrue(reactivada);
    }

    @Test
    public void reactivarUbicacionActivaDelHistorial_E2_5_3_SeNotificaQueEsaYaEstabaActivada() throws AlreadyHasPlaceException {
        //Arrange
        Ubicacion ubicacionABorrar = usuario.altaUbicacionToponimo("Castellón");
        //Act
        List<Ubicacion> historialUbicaciones = usuario.getHistorialUbicaciones();
        boolean reactivada = usuario.reactivarUbicacionDelHistorial(ubicacionABorrar.getNombre());
        //Assert
        assertFalse(reactivada);
    }
}
