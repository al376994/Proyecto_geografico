package Geografico.Aceptacion;

import Geografico.model.API.*;
import Geografico.model.Ciudad;
import Geografico.model.Prevision;
import Geografico.model.TiempoActual;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_10_2 {
    private Usuario usuario;
    private APIAirVisualInterface APIAirVisual;
    private APIOpenWeatherInterface APIOpenWeather;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APIAirVisual = new APIAirVisual();
        APIOpenWeather = new APIOpenWeather();
    }

    @Test
    public void verTiempoUbiActual_E10_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        Ciudad ciudadCercana = APIAirVisual.getCiudadCercana();
        //Act
        TiempoActual p = APIOpenWeather.getTiempoActual(ciudadCercana);
        //Assert
        assertEquals(true, p.gettActual()!=null);
    }

    @Test
    public void verTiempoUbiActual_E10_2_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        //Arrange
        Ciudad ciudadCercana = null;
        //Act
        TiempoActual p = APIOpenWeather.getTiempoActual(ciudadCercana);
        //Assert
        assertEquals(null, p);
    }
}
