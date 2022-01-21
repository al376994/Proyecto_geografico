package Geografico.Integracion;

import Geografico.model.API.APISportsData;
import Geografico.model.API.APISportsDataInterface;
import Geografico.model.Equipo;
import Geografico.model.EquipoClasificacion;
import Geografico.model.Partido;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_8_1 {
    private Usuario usuario;
    private APISportsDataInterface mockedAPISportsData;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFt");
        mockedAPISportsData = Mockito.mock(APISportsData.class);
    }

    @Test
    public void seleccionarLiga_E8_1_1_seleccionCorrecta() throws SQLException, CoordenadasExcepcion, JSONException {
        //Arrange
        List<String> ligas = new ArrayList<>();
        ligas.add("Liga española");
        ligas.add("Champions league");
        List<Partido> partidos1 = new ArrayList<>();
        partidos1.add(new Partido(new Equipo(), new Equipo(), "estadio", "resultado", "fecha"));
        partidos1.add(new Partido(new Equipo(), new Equipo(), "estadio", "resultado", "fecha"));
        when(mockedAPISportsData.getLigas()).thenReturn(ligas);
        List<String> ligasDisponibles = mockedAPISportsData.getLigas();
        System.out.println(ligasDisponibles);
        //Act
        when(mockedAPISportsData.getPartidosUsuario(ligasDisponibles.get(0), false)).thenReturn(partidos1);
        Boolean elegida = mockedAPISportsData.elegirLiga(usuario.getNombre(),ligasDisponibles.get(0));
        List<Partido> partidos = mockedAPISportsData.getPartidosUsuario(ligasDisponibles.get(0), false);
        //Assert
        assertEquals(true, partidos.size()>1);
    }

    @Test
    public void seleccionarLiga_E8_1_2_seleccionIncorrecta() throws SQLException, JSONException {
        //simulamos que no hay conexión con la API
        mockedAPISportsData = null;
        //Arrange
        List<String> ligas = new ArrayList<>();
        ligas.add("Liga española");
        ligas.add("Champions league");
        List<Partido> partidos1 = new ArrayList<>();
        partidos1.add(new Partido(new Equipo(), new Equipo(), "estadio", "resultado", "fecha"));
        partidos1.add(new Partido(new Equipo(), new Equipo(), "estadio", "resultado", "fecha"));
        assertThrows(NullPointerException.class,
                ()-> {
                    List<String> ligasDisponibles = mockedAPISportsData.getLigas();
                });
        //Act

        //Assert
        //cortamos la conexión con la API
    }
}
