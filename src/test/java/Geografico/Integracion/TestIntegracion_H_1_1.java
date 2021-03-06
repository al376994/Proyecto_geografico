package Geografico.Integracion;

import Geografico.model.*;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestIntegracion_H_1_1 {
    private APIGeocodeInterface mockedApiGeocode;
    private ListaUsuario mockedListaUsuario;
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;

    @BeforeEach
    void setUp() throws SQLException {
        mockedApiGeocode = Mockito.mock(APIGeocodeInterface.class);
        mockedListaUsuario = Mockito.mock(ListaUsuario.class);
        mockedDataBaseFunctions = Mockito.mock((DataBaseFunctions.class));
        when(mockedListaUsuario.getUsuario("Nombre1", null)).thenReturn(new Usuario("Nombre1"));
        usuario = mockedListaUsuario.getUsuario("Nombre1", null);
        usuario.setDataBaseFunctions(mockedDataBaseFunctions);
    }


    @Test
    public void altaUbicacionToponimo_E1_1_1_seAñadeLista() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        //Act
        ArrayList lista = new ArrayList();
        lista.add(new Ubicacion());
        when(mockedDataBaseFunctions.listarUbicacionesUsuario("Nombre1")).thenReturn(lista);
        when(mockedApiGeocode.getUbicacionToponimo("Castellón")).thenReturn(new Ubicacion());
        usuario.altaUbicacionToponimo("Castellón");
        //Assert
        List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
        assertEquals(1, listaUbicaciones.size());
    }

    @Test
    public void altaUbicacionToponimo_E1_1_2_listaUbicacionesVacia() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        usuario.addAPIGeocode(mockedApiGeocode);
        //Act
        ArrayList lista = new ArrayList();
        when(mockedDataBaseFunctions.listarUbicacionesUsuario("Nombre1")).thenReturn(lista);
        when(mockedApiGeocode.getUbicacionToponimo(";")).thenReturn(null);
        usuario.altaUbicacionToponimo(";");
        //Assert
        List<Ubicacion> listaUbicaciones = usuario.getUbicaciones();
        assertEquals(0, listaUbicaciones.size());
    }
}
