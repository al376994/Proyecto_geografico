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

public class TestsAceptacion_H_9_1 {
    private Usuario usuario;
    private APIAirVisualInterface APIAirVisual;
    private APIOpenDataSoftInterface APIOpenDataSoft;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APIAirVisual = new APIAirVisual();
        APIOpenDataSoft = new APIOpenDataSoft();
    }

    @Test
    public void verTiempoCapitalesProvincia_E9_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        List<Ciudad> ciudades = APIOpenDataSoft.getCapitales();
        System.out.println(ciudades);
        //Act
        List<Prevision> previsiones = new ArrayList<>();
//        for (Ciudad c:ciudades){
//            Prevision p = new Prevision(APIAirVisual.getTiempo(c.getNombre(), c.getProvincia(), c.getPais()));
//        }
        //Assert
        assertEquals(true, previsiones.size()>1);
    }

    @Test
    public void verTiempoCapitalesProvincia_E9_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        APIOpenDataSoft = null;
        //Arrange
        List<Ciudad> ciudades = APIOpenDataSoft.getCapitales();
        System.out.println(ciudades);
        //Act
        List<Prevision> previsiones = new ArrayList<>();
//        for (Ciudad c:ciudades){
//            Prevision p = new Prevision(APIAirVisual.getTiempo(c.getNombre(), c.getProvincia(), c.getPais()));
//        }
        //Assert
        assertEquals(0, previsiones.size());
    }
}
