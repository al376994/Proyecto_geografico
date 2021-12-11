package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestsAceptacion_H_3_1 {

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_1_SeMuestranLosDisponibles() {
        //Arrange
        System.out.println("1");
        Connection conn = DataBaseConnector.getConnection();
        DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(conn);

        dataBaseFunctions.addServicioAPIDisponible("AirVisual");
        dataBaseFunctions.addServicioAPIDisponible("Currents");

        Usuario usuario = new Usuario("NuevoUser");
        //Act
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();

        dataBaseFunctions.deleteServicioAPIDisponible("AirVisual");
        dataBaseFunctions.deleteServicioAPIDisponible("Currents");

        //Assert
        assertEquals(2, APIsDisponibles.size());
    }

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_2_SeNotificaQueNoHayDisponibles() {
        //Arrange
        Usuario usuario = new Usuario("NuevoUser");
        //Act
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        //Assert
        assertEquals(0, APIsDisponibles.size());
    }
}
