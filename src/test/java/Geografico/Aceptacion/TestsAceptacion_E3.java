package Geografico.Aceptacion;

import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIAirVisualInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestsAceptacion_E3 {
    private APIAirVisualInterface apiAirVisual;

    @BeforeEach
    public void iniciarVariables(){
        apiAirVisual = new APIAirVisual();
    }

    @Test
    public void validarToponimo_E1_3_1_devuelveTrue(){
        //Arrange
        //Act
        Boolean valido = apiAirVisual.validarToponimo("Castellón");

        //esto de aqui debajo lo hace valido si se descomenta y tecnicamente funciona como debería
        //valido = apiAirVisual.validarToponimoExaustivo("Castello%20de%20la%20Plana", "Valencia", "Spain");
        //Assert
        assertEquals(true, valido);
    }

    @Test
    public void validarToponimo_E1_3_1_devuelveFalse(){
        //Arrange
        //Act
        Boolean valido = apiAirVisual.validarToponimo(";");
        //Assert
        //salta excepcion
        assertEquals(false, valido);
    }
}
