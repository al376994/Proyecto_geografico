package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_E4 {
    private APIGeocodeInterface apiGeocode;

    @BeforeEach
    public void iniciarVariables(){
        //apiAirVisual = new APIAirVisual();
        apiGeocode = new APIGeocode();
    }

    @Test
    public void validarCoordenadas_E1_4_1_devuelveTrue(){
        //Arrange
        //Act
        boolean valido = apiGeocode.validarCoordenadas(39.986, -0.0376709);
        //Assert
        assertTrue(valido);
    }

    @Test
    public void validarCoordenadas_E1_4_2_devuelveFalse(){
        //Arrange
        //Act
        boolean valido = apiGeocode.validarCoordenadas(100, -100);
        //Assert
        assertFalse(valido);
    }
}
