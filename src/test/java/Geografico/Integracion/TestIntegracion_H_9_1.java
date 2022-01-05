package Geografico.Integracion;

import Geografico.model.API.*;
import Geografico.model.Ciudad;
import Geografico.model.Prevision;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegracion_H_9_1 {
    private Usuario usuario;
    private APIOpenWeatherInterface mockedAPIOpenWeather;
    private APIOpenDataSoftInterface mockedAPIOpenDataSoft;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        mockedAPIOpenWeather = Mockito.mock(APIOpenWeather.class);
        mockedAPIOpenDataSoft = Mockito.mock(APIOpenDataSoft.class);
    }

    @Test
    public void verTiempoCapitalesProvincia_E9_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        List<Ciudad> aux = new ArrayList<>();
        Ciudad ciudad = new Ciudad("valencia","valencia","valencia","valencia");
        aux.add(ciudad);
        List<Prevision> auxP = new ArrayList<>();
        auxP.add(new Prevision("10","5","50","soleado",ciudad));
        when(mockedAPIOpenWeather.getPrevisionDiaria(ciudad)).thenReturn(auxP);
        when(mockedAPIOpenDataSoft.getCapitales()).thenReturn(aux);

        List<Ciudad> ciudades = mockedAPIOpenDataSoft.getCapitales();
        System.out.println(ciudades);
        //Act
        List<List<Prevision>> previsiones = new ArrayList<>();
        for (Ciudad c:ciudades){
            List<Prevision> pr = mockedAPIOpenWeather.getPrevisionDiaria(c);
            previsiones.add(pr);
        }
        //Assert
        assertEquals(true, previsiones.size()>0);
    }

    @Test
    public void verTiempoCapitalesProvincia_E9_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        mockedAPIOpenDataSoft = null;
        //Arrange
        assertThrows(NullPointerException.class,
                ()-> {
                    List<Ciudad> ciudades = mockedAPIOpenDataSoft.getCapitales();
        });
        //Act
        //Assert
    }
}
