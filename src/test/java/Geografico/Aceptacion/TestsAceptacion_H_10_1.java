package Geografico.Aceptacion;

import Geografico.model.API.*;
import Geografico.model.Ciudad;
import Geografico.model.Polucion;
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

public class TestsAceptacion_H_10_1 {
    private Usuario usuario;
    private APIAirVisualInterface APIAirVisual;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APIAirVisual = new APIAirVisual();
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException, FileNotFoundException {
        //Arrange
        Polucion p = APIAirVisual.getPolucionCiudadCercana();
        //Act

        //Assert
        assertEquals(true, p.getMaincn()!=null);
    }

    @Test
    public void verCalidadAireUbiActual_E10_1_2_Incorrecta() throws SQLException, JSONException, FileNotFoundException {
        //Arrange
        APIAirVisual = null;
        assertThrows(NullPointerException.class,
                ()-> {
                    Polucion p = APIAirVisual.getPolucionCiudadCercana();
        });
        //Act
        //Assert
    }
}
