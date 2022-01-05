package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestsAceptacion_H_1_8 {
    private Usuario usuario;
    private APIGeocodeInterface apiGeocode;
    private ListaUsuario listaUsuario;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        apiGeocode = new APIGeocode();
        listaUsuario = new ListaUsuario();
        usuario = new Usuario();
        usuario.setNombre("usuario5");
        usuario.setContrasena("usuario5");
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
    public void asignarAliasUbicacion_E1_8_1_devuelveAliasCorrecto() throws CoordenadasExcepcion, AlreadyHasPlaceException {
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act
        usuario.asignarAliasUbicacion("Castello De La Plana", "casa");
        //Assert
        assertEquals("casa", usuario.getAliasUbicacion("Castello De La Plana"));
    }
    @Test
    public void asignarAliasUbicacion_E1_8_2_devuelveAliasIncorrecto() throws CoordenadasExcepcion, AlreadyHasPlaceException {
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act

        assertThrows(IllegalArgumentException.class,
                ()-> usuario.asignarAliasUbicacion("Castello de la Plana", ""));
        //Assert
//        assertEquals(usuario.getAliasUbicacion("Castellon de la Plana"), "casa");
    }
}
