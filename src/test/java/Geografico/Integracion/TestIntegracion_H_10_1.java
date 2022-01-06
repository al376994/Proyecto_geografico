package Geografico.Integracion;

import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIAirVisualInterface;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.Polucion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegracion_H_10_1 {
    private Usuario usuario;
    private APIAirVisualInterface mockedAPIAirVisual;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        mockedAPIAirVisual = Mockito.mock(APIAirVisual.class);
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        Polucion aux = new Polucion(1,2, "test", "test");
        when(mockedAPIAirVisual.getPolucionCiudadCercana()).thenReturn(aux);
        Polucion p = mockedAPIAirVisual.getPolucionCiudadCercana();
        //Act

        //Assert
        assertEquals(true, p.getMaincn()!=null);
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        //Arrange
        when(mockedAPIAirVisual.getPolucionCiudadCercana()).thenReturn(null);
        Polucion p = mockedAPIAirVisual.getPolucionCiudadCercana();
        //Act
        assertEquals(null, p);
        //Assert
    }
}
