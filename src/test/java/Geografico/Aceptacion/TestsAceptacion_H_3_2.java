package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_3_2 {

    private void cleanDataBase() {

    }

    @Test
    public void activarServicioAPIDisponible_E3_2_1_SeActivaElServicio(){
        //Arrange
        Connection conn = DataBaseConnector.getConnection();
        DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(conn);

        dataBaseFunctions.addServicioAPIDisponible("AirVisual");
        dataBaseFunctions.addServicioAPIDisponible("Currents");

        Usuario usuario = new Usuario("nuevoUsuario");
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();

        //Act
        boolean activado = usuario.activarServicioAPI(APIsDisponibles.get(0));

        //Lo siguiente se debe ejecutar para dejar la base de datos igual que antes del test.
        //##################################################################################
        boolean desactivado = usuario.desactivarServicioAPI(APIsDisponibles.get(0));
        dataBaseFunctions.deleteServicioAPIDisponible("AirVisual");
        dataBaseFunctions.deleteServicioAPIDisponible("Currents");

        //Assert
        assertTrue(activado);
    }

    @Test
    public void activarServicioAPIDisponible_E3_2_3_NoHayServiciosParaActivar(){
        //Arrange
        Usuario usuario = new Usuario("nuevoUsuario");
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        //Act
        //Assert
        assertEquals(0, APIsDisponibles.size());
    }
}
