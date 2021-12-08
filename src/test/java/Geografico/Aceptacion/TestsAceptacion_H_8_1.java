package Geografico.Aceptacion;

import Geografico.model.API.APISportsData;
import Geografico.model.API.APISportsDataInterface;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_8_1 {
    private Usuario usuario;
    private APISportsDataInterface APISportsData;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APISportsData = new APISportsData();
    }

    @Test
    public void seleccionarLiga_E8_2_1_seleccionCorrecta() throws SQLException, CoordenadasExcepcion {
        //Arrange
        List<String> ligasDisponibles = APISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        Boolean elegida = APISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        List<String> partidos = APISportsData.getPartidosLiga(ligasDisponibles.get(0));
        //Assert
        assertEquals(true, elegida);
    }

    @Test
    public void seleccionarLiga_E8_2_2_seleccionIncorrecta() throws SQLException {
        //Arrange
        List<String> ligasDisponibles = APISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        Boolean elegida = APISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        List<String> partidos = APISportsData.getPartidosLiga(ligasDisponibles.get(0));
        //Assert
        //cortamos la conexión con la API
        assertEquals(false, elegida);
    }
}
