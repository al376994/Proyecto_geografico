package Geografico.Integracion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_7_2 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void desactivarLocacalizacionFavorita_E7_2_1_desactivadoCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        List<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Castellon"));
        when(mockedDataBaseFunctions.getUbicacionesFavoritas(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> listaUbicaciones = mockedDataBaseFunctions.getUbicacionesFavoritas(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);

        //Act
        when(mockedDataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(),castellon.getNombre())).thenReturn(true);
        Boolean anadido = mockedDataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertEquals(true, anadido);
    }

    @Test
    public void desactivarLocacalizacionFavorita_E7_2_2_desactivadoIncorrecto() throws SQLException {
        //Arrange
        List<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Castellon"));
        when(mockedDataBaseFunctions.getUbicacionesFavoritas(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> listaUbicaciones = mockedDataBaseFunctions.getUbicacionesFavoritas(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);
        //Act
        when(mockedDataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(),castellon.getNombre())).thenReturn(false);
        Boolean anadido = mockedDataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertEquals(false, anadido);
    }
}
