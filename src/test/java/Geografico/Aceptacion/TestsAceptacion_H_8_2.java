package Geografico.Aceptacion;

import Geografico.model.API.APISportsData;
import Geografico.model.API.APISportsDataInterface;
import Geografico.model.EquipoClasificacion;
import Geografico.model.Partido;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_8_2 {
    private Usuario usuario;
    private APISportsDataInterface APISportsData;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        APISportsData = new APISportsData();
    }

    @Test
    public void visualizarClasificacion_E8_2_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException {
        //Arrange
        List<String> ligasDisponibles = APISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        Boolean elegida = APISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        List<EquipoClasificacion> equipos = APISportsData.getClasificacionUsuario(ligasDisponibles.get(0));
        //Assert
        assertEquals(true, equipos.size()>1);
    }

    @Test
    public void visualizarClasificacion_E8_2_2_Incorrecta() throws SQLException, JSONException {
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
