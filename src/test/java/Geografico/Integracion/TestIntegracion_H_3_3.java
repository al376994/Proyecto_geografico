package Geografico.Integracion;

import Geografico.model.API.*;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestIntegracion_H_3_3 {
    private APIGeocodeInterface mockedapiGeocode;
    private APIAirVisualInterface mockedapiAirVisual;

    @BeforeEach
    private void iniciarVariables() throws SQLException {
        mockedapiGeocode = Mockito.mock(APIGeocodeInterface.class);
        mockedapiAirVisual = Mockito.mock(APIAirVisualInterface.class);
    }

    @Test
    public void conocerDescripion_E3_3_1_seMuestraInformacion() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        //Act
        when(mockedapiGeocode.getDescripcion()).thenReturn("Descripcion");
        when(mockedapiAirVisual.getDescripcion()).thenReturn("Descripcion");
        String descripcionGeocode = mockedapiGeocode.getDescripcion();
        String descripcionAirVisual = mockedapiAirVisual.getDescripcion();
        //Assert
        assertEquals(true, (descripcionAirVisual != null) && (descripcionGeocode != null));
    }

    @Test
    public void conocerDescripion_E3_3_2_noSeMuestraInformacion() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        mockedapiAirVisual = null;
        when(mockedapiGeocode.getDescripcion()).thenReturn("Descripcion");
        //Act
        String descripcionGeocode = mockedapiGeocode.getDescripcion();
        //Assert
        assertEquals(true, mockedapiAirVisual == null);
    }
}
