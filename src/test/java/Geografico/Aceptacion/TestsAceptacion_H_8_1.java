package Geografico.Aceptacion;

import Geografico.model.*;
import Geografico.model.API.APISportsData;
import Geografico.model.API.APISportsDataInterface;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_8_1 {
    private Usuario usuario;
    private APISportsDataInterface APISportsData;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APISportsData = new APISportsData();
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        iniciarVariables();
        APISportsData.quitarLiga(usuario.getNombre(), APISportsData.getLigas().get(0));
    }

    @Test
    public void seleccionarLiga_E8_1_1_seleccionCorrecta() throws SQLException, CoordenadasExcepcion, JSONException {
        //Arrange
        List<String> ligasDisponibles = APISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        Boolean elegida = APISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        List<Partido> partidos = APISportsData.getPartidosUsuario(ligasDisponibles.get(0), false);
        //Assert
        assertEquals(true, partidos.size()>1);
    }

    @Test
    public void seleccionarLiga_E8_1_2_seleccionIncorrecta() throws SQLException, JSONException {
        //simulamos que no hay conexión con la API
        APISportsData = null;
        //Arrange
        assertThrows(NullPointerException.class,
                ()-> {
                    List<String> ligasDisponibles = APISportsData.getLigas();
        });
        //Act
        //Assert
        //cortamos la conexión con la API
    }
}
