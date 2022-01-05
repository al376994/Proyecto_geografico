package Geografico.Integracion;

import Geografico.model.API.APISportsData;
import Geografico.model.API.APISportsDataInterface;
import Geografico.model.DataBaseFunctions;
import Geografico.model.EquipoClasificacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegracion_H_8_2 {
    private Usuario usuario;
    private APISportsDataInterface mockedAPISportsData;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        mockedAPISportsData = Mockito.mock(APISportsData.class);
    }

    @Test
    public void visualizarClasificacion_E8_2_1_Correcta() throws SQLException, CoordenadasExcepcion, JSONException {
        //Arrange
        List<String> ligas = new ArrayList<>();
        ligas.add("Liga española");
        ligas.add("Champions league");
        List<EquipoClasificacion> equipos1 = new ArrayList<>();
        equipos1.add(new EquipoClasificacion("Vila", "es", "id", "logo", 20, 0, 0, 0));
        equipos1.add(new EquipoClasificacion("Vila", "es", "id", "logo", 20, 0, 0, 0));
        when(mockedAPISportsData.getLigas()).thenReturn(ligas);
        List<String> ligasDisponibles = mockedAPISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        when(mockedAPISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0))).thenReturn(true);
        Boolean elegida = mockedAPISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        when(mockedAPISportsData.getClasificacionUsuario(ligasDisponibles.get(0))).thenReturn(equipos1);
        List<EquipoClasificacion> equipos = mockedAPISportsData.getClasificacionUsuario(ligasDisponibles.get(0));
        //Assert
        assertEquals(true, equipos.size()>1);
    }

    @Test
    public void visualizarClasificacion_E8_2_2_Incorrecta() throws SQLException, JSONException {
        //simulamos que no hay conexión con la API
        mockedAPISportsData = null;
        //Arrange
        List<String> ligas = new ArrayList<>();
        ligas.add("Liga española");
        ligas.add("Champions league");
        List<EquipoClasificacion> equipos1 = new ArrayList<>();
        equipos1.add(new EquipoClasificacion("Vila", "es", "id", "logo", 20, 0, 0, 0));
        equipos1.add(new EquipoClasificacion("Vila", "es", "id", "logo", 20, 0, 0, 0));
        assertThrows(NullPointerException.class,
                ()-> {
                    List<String> ligasDisponibles = mockedAPISportsData.getLigas();
                });
        //Act
        //Assert
    }
}
