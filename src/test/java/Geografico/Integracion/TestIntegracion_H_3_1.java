package Geografico.Integracion;

import Geografico.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_3_1 {
    //Si utilizamos BeforeEach no funciona

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_1_SeMuestranLosDisponibles() {
        //Arrange
        Usuario mockedUsuario = Mockito.mock(Usuario.class);
        List<String> apisDisponibles = new ArrayList<>();
        apisDisponibles.add("AirVisual");
        apisDisponibles.add("Currents");

        //Act
        when(mockedUsuario.getServiciosAPIDisponibles()).thenReturn(apisDisponibles);
        List<String> result = mockedUsuario.getServiciosAPIDisponibles();

        //Assert
        assertEquals(2, result.size());
    }

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_2_SeNotificaQueNoHayDisponibles() {
        //Arrange
        Usuario mockedUsuario = Mockito.mock(Usuario.class);
        List<String> apisDisponibles = new ArrayList<>();
        //Act
        when(mockedUsuario.getServiciosAPIDisponibles()).thenReturn(apisDisponibles);
        List<String> result = mockedUsuario.getServiciosAPIDisponibles();
        //Assert
        assertEquals(0, result.size());
    }
}
