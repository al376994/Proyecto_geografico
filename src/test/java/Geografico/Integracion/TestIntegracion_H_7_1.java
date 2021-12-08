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
import static org.mockito.Mockito.*;

public class TestIntegracion_H_7_1 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void anadirLocacalizacionFavorita_E7_1_1_anadidoCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        List<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Castellon"));
        when(mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> listaUbicaciones = mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);
        //Act
        when(mockedDataBaseFunctions.anadirUbicacionFavorita(usuario.getNombre(),castellon.getNombre())).thenReturn(true);
        Boolean anadido = mockedDataBaseFunctions.anadirUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertEquals(true, anadido);
    }

    @Test
    public void anadirLocacalizacionFavorita_E7_1_2_anadidoIncorrecto() throws SQLException {
        //Arrange
        //Act
        when(mockedDataBaseFunctions.anadirUbicacionFavorita(usuario.getNombre(),"sinNombre")).thenReturn(false);
        Boolean anadido = mockedDataBaseFunctions.anadirUbicacionFavorita(usuario.getNombre(), "sinNombre");
        //Assert
        assertEquals(false, anadido);
    }
}
