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

import static org.junit.jupiter.api.Assertions.*;

public class TestsAceptacion_H_10_2 {
    private Usuario usuario;
    private APIAirVisualInterface APIAirVisual;
    private APIOpenWeatherInterface APIOpenWeather;
    private APIOpenDataSoft apiOpenDataSoft;


    @BeforeEach
    private void iniciarVariables() {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APIAirVisual = new APIAirVisual();
        APIOpenWeather = new APIOpenWeather();
        apiOpenDataSoft = new APIOpenDataSoft();
    }

    @Test
    public void verTiempoUbiActual_E10_2_1_Correcta() throws JSONException {
        //Arrange
        Ciudad ciudadCercana = APIAirVisual.getCiudadCercana();
        Ciudad aux = apiOpenDataSoft.cambiarParaOpenWeather(ciudadCercana);
        //Act
        TiempoActual p = APIOpenWeather.getTiempoActual(aux);
        //Assert
        assertNotNull(p.gettActual());
    }

    @Test
    public void verTiempoUbiActual_E10_2_2_Incorrecta() {
        //Arrange
        Ciudad ciudadCercana = null;
        //Act
        assertThrows(NullPointerException.class,
                ()-> APIOpenWeather.getTiempoActual(ciudadCercana));
        //Assert
    }
}
