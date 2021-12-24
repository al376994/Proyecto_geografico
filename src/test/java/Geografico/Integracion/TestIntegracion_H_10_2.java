package Geografico.Integracion;

import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIAirVisualInterface;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.API.APIOpenWeatherInterface;
import Geografico.model.Ciudad;
import Geografico.model.TiempoActual;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegracion_H_10_2 {
    private Usuario usuario;
    private APIAirVisualInterface mockedAPIAirVisual;
    private APIOpenWeatherInterface mockedAPIOpenWeather;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        mockedAPIAirVisual = Mockito.mock(APIAirVisual.class);
        mockedAPIOpenWeather = Mockito.mock(APIOpenWeather.class);
    }

    @Test
    public void verTiempoUbiActual_E10_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        Ciudad aux1 = new Ciudad("Valencia","valencia"
                ,"valencia","valencia");
        TiempoActual aux2 = new TiempoActual("Bien","1","1","1","1");
        when(mockedAPIAirVisual.getCiudadCercana()).thenReturn(aux1);
        when(mockedAPIOpenWeather.getTiempoActual(aux1)).thenReturn(aux2);
        Ciudad ciudadCercana = mockedAPIAirVisual.getCiudadCercana();
        //Act
        TiempoActual p = mockedAPIOpenWeather.getTiempoActual(ciudadCercana);
        //Assert
        assertEquals(true, p.gettActual()!=null);
    }

    @Test
    public void verTiempoUbiActual_E10_2_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        //Arrange
        Ciudad ciudadCercana = null;
        when(mockedAPIOpenWeather.getTiempoActual(ciudadCercana)).thenReturn(null);
        //Act
        TiempoActual p = mockedAPIOpenWeather.getTiempoActual(ciudadCercana);
        //Assert
        assertEquals(null, p);
    }
}
