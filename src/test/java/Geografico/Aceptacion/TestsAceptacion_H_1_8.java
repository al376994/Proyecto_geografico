package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.Rule;
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


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        apiGeocode = new APIGeocode();
        usuario = new Usuario();			//para hacer tests al principio creamos un usuario a mano
        usuario.setNombre("usuario5");
    }

    @Test
    public void asignarAliasUbicacion_E1_8_1_devuelveAliasCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act
        usuario.asignarAliasUbicacion("Castellon de la Plana", "casa");
        //Assert
        assertEquals(usuario.getAliasUbicacion("Castellon de la Plana"), "casa");
    }
    @Test
    public void asignarAliasUbicacion_E1_8_2_devuelveAliasIncorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(apiGeocode);
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act

        assertThrows(IllegalArgumentException.class,
                ()->{
                    usuario.asignarAliasUbicacion("Castellon de la Plana", "");
        });
        //Assert
//        assertEquals(usuario.getAliasUbicacion("Castellon de la Plana"), "casa");
    }
}
