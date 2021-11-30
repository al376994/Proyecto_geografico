package Geografico.Integracion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;


@SpringBootTest
public class TestIntegracion_H_1_7 {
    private APIGeocodeInterface mockedApiGeocode;
    private Usuario usuario;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
        usuario = new Usuario();			//para hacer tests al principio creamos un usuario a mano
        usuario.setNombre("usuario5");
    }

    @Test
    public void obtenerToponimoCercanoCoordenadas_E1_7_1_devuelveToponimo() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        //Act
        when(mockedApiGeocode.getUbicacionCoordenadas(39.986,-0.0376709)).thenReturn(new Ubicacion("Castellón de la Plana"));
        String ubicacion = usuario.obtenerToponimoCercanoCoordenadas(39.986,-0.0376709);
        //Assert
        assertEquals("Castellón de la Plana", ubicacion);
    }

    @Test
    public void obtenerToponimoCercanoCoordenadas_E1_7_2_toponimoInvalido(){
        //https://stackoverflow.com/questions/40268446/junit-5-how-to-assert-an-exception-is-thrown
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        //Act
        assertThrows(NullPointerException.class,
                ()->{
                    String ubicacion = usuario.obtenerToponimoCercanoCoordenadas(100,-100);
                });
    }
}
