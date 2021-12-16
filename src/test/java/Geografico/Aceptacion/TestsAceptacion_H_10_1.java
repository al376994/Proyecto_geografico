package Geografico.Aceptacion;

import Geografico.model.API.APIOpenDataSoft;
import Geografico.model.API.APIOpenDataSoftInterface;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.API.APIOpenWeatherInterface;
import Geografico.model.Ciudad;
import Geografico.model.Prevision;
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

public class TestsAceptacion_H_10_1 {
    private Usuario usuario;
    private APIOpenWeatherInterface APIOpenWeather;
    private APIOpenDataSoftInterface APIOpenDataSoft;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APIOpenWeather = new APIOpenWeather();
        APIOpenDataSoft = new APIOpenDataSoft();
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        List<Ciudad> ciudades = APIOpenDataSoft.getCapitalesCV();
        System.out.println(ciudades);
        //Act
        List<List<Prevision>> previsiones = new ArrayList<>();
        for (Ciudad c:ciudades){
            List<Prevision> pr = APIOpenWeather.getPrevisionDiaria(c);
            previsiones.add(pr);
        }
        //Assert
        assertEquals(0, previsiones.size());
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        APIOpenDataSoft = null;
        //Arrange
        List<Ciudad> ciudades = APIOpenDataSoft.getCapitales();
        System.out.println(ciudades);
        //Act
        List<List<Prevision>> previsiones = new ArrayList<>();
        for (Ciudad c:ciudades){
            List<Prevision> pr = APIOpenWeather.getPrevisionDiaria(c);
            previsiones.add(pr);
        }
        //Assert
        assertEquals(0, previsiones.size());
    }
}
