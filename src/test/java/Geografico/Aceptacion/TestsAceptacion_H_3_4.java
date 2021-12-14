package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestsAceptacion_H_3_4 {

    @Test
    public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_1_SeDesactivaLaAPIParaEsteUsuario() throws SQLException {
        //Arrange
        Connection conn = DataBaseConnector.getConnection();
        DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(conn);

        dataBaseFunctions.addServicioAPIDisponible("AirVisual1");
        dataBaseFunctions.addServicioAPIDisponible("Currents1");

        Usuario usuario = new Usuario("nuevoUsuario");
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        boolean activado = usuario.activarServicioAPI(APIsDisponibles.get(0));

        //Act
        boolean desactivado = usuario.desactivarServicioAPI(APIsDisponibles.get(0));

        dataBaseFunctions.deleteServicioAPIDisponible("AirVisual1");
        dataBaseFunctions.deleteServicioAPIDisponible("Currents1");

        //Assert
        assertTrue(desactivado);
    }

    @Test
    public void desactivarAPIQueYaNoInteresaAlUsuario_E3_4_2_NoTieneAPIsActivasNoSeDesactivaNada() throws SQLException {
        //Arrange
        Usuario usuario = new Usuario("nuevoUsuario");
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        //Act
        //Assert
        assertEquals(0, APIsDisponibles.size());
    }
}
