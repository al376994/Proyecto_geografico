package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestsAceptacion_H_1_7 {
    private APIGeocodeInterface apiGeocode;
    private ListaUsuario listaUsuario;
    private Usuario usuario;



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
    public void obtenerToponimoCercanoCoordenadas_E1_7_1_devuelveToponimo() throws CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        //Act
        Ubicacion ubicacion = apiGeocode.getUbicacionCoordenadas(39.986,-0.0376709);
        //Assert
        assertEquals("Castello De La Plana", ubicacion.getNombre());
    }

    @Test
    public void obtenerToponimoCercanoCoordenadas_E1_7_2_toponimoInvalido(){
        //https://stackoverflow.com/questions/40268446/junit-5-how-to-assert-an-exception-is-thrown
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        //Act

        assertThrows(CoordenadasExcepcion.class,
                ()->{
                    Ubicacion ubicacion = apiGeocode.getUbicacionCoordenadas(100,-100);
        });
    }
}
