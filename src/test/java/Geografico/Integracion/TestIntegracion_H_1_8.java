package Geografico.Integracion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestIntegracion_H_1_8 {
    private Usuario usuario;
    private APIGeocodeInterface mockedApiGeocode;
    private DataBaseFunctions mockedDataBase;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
        mockedDataBase = Mockito.mock(DataBaseFunctions.class);
        usuario = new Usuario();			//para hacer tests al principio creamos un usuario a mano
        usuario.setNombre("usuario5");
    }

    @Test
    public void asignarAliasUbicacion_E1_8_1_devuelveAliasCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        
        when(mockedDataBase.altaAliasUbicacion(usuario.getNombre(),"Castellon de la Plana", "casa")).thenReturn(true);
        when(mockedDataBase.getAliasUbicacion(usuario.getNombre(),"Castellon de la Plana")).thenReturn("casa");
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act
        Boolean aceptada = usuario.asignarAliasUbicacion("Castellon de la Plana", "casa");
        //Assert
        assertEquals(true, aceptada);
    }
    @Test
    public void asignarAliasUbicacion_E1_8_2_devuelveAliasIncorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        usuario.altaUbicacionCoordenadas(39.986,-0.0376709);
        //Act
        assertThrows(IllegalArgumentException.class,
                ()->{
                    usuario.asignarAliasUbicacion("Castellon de la Plana", "");
        });
        //Assert
    }
}
