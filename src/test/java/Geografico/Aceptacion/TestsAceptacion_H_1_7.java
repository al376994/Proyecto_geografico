package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.CookieHandler;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TestsAceptacion_H_1_7 {
    private APIGeocodeInterface apiGeocode;
    private Usuario usuario;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        apiGeocode = new APIGeocode();
        usuario = new Usuario();			//para hacer tests al principio creamos un usuario a mano
        usuario.setNombre("usuario5");
    }

    @Test
    public void obtenerToponimoCercanoCoordenadas_E1_7_1_devuelveToponimo() throws SQLException, CoordenadasExcepcion {
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
