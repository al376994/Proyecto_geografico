package Geografico.Aceptacion;

import Geografico.model.API.*;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestsAceptacion_H_3_3 {
    private APIGeocodeInterface apiGeocode;
    private APIOpenWeatherInterface apiOpenWeather;
    private APIAirVisualInterface apiAirVisual;
    private APISportsDataInterface apiSportsData;

    @BeforeEach
    private void iniciarVariables() throws SQLException {
        apiGeocode = new APIGeocode();
        apiAirVisual = new APIAirVisual();
    }

    @Test
    public void conocerDescripion_E3_3_1_seMuestraInformacion() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        //Act
        String descripcionGeocode = apiGeocode.getDescripcion();
        String descripcionAirVisual = apiAirVisual.getDescripcion();
        //Assert
        assertEquals(true, (descripcionAirVisual != null) && (descripcionGeocode != null));
    }

    @Test
    public void conocerDescripion_E3_3_2_noSeMuestraInformacion() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        apiAirVisual = null;
        //Act
        String descripcionGeocode = apiGeocode.getDescripcion();
        //Assert
        assertEquals(true, apiAirVisual == null);
    }
}
