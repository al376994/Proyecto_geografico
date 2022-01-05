package Geografico.Aceptacion;

import Geografico.model.API.*;
import Geografico.model.Ciudad;
import Geografico.model.EquipoClasificacion;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_9_1 {
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
    public void verTiempoCapitalesProvincia_E9_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        List<Ciudad> ciudades = APIOpenDataSoft.getCapitales();
//        System.out.println(ciudades);
        //Act
        List<List<Prevision>> previsiones = new ArrayList<>();
        for (Ciudad c:ciudades){
            System.out.println(c.getNombre());
            List<Prevision> pr = APIOpenWeather.getPrevisionDiaria(c);
            previsiones.add(pr);
        }
        //Assert
        assertEquals(true, previsiones.size()>1);
    }

    @Test
    public void verTiempoCapitalesProvincia_E9_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        APIOpenDataSoft = null;
        //Arrange
        assertThrows(NullPointerException.class,
                ()-> {
                    List<Ciudad> ciudades = APIOpenDataSoft.getCapitales();
                });
        //Act
//        List<List<Prevision>> previsiones = new ArrayList<>();
//        for (Ciudad c:ciudades){
//            List<Prevision> pr = APIOpenWeather.getPrevisionDiaria(c);
//            previsiones.add(pr);
//        }
        //Assert
//        assertEquals(0, previsiones.size());
    }
}
