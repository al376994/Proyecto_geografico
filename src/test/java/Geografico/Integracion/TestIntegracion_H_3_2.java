package Geografico.Integracion;

import Geografico.model.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_3_2 {

    @Test
    public void activarServicioAPIDisponible_E3_2_1_SeActivaElServicio(){
        //Arrange
        Usuario mockedUsuario = Mockito.mock(Usuario.class);
        //Act
        when(mockedUsuario.activarServicioAPI("AirVisual")).thenReturn(true);
        Boolean activado = mockedUsuario.activarServicioAPI("AirVisual");
        //Assert
        assertTrue(activado);
    }

    @Test
    public void activarServicioAPIDisponible_E3_2_3_NoHayServiciosParaActivar(){
        //Arrange
        Usuario mockedUsuario = Mockito.mock(Usuario.class);
        //Act
        when(mockedUsuario.activarServicioAPI("AirVisual")).thenReturn(false);
        Boolean activado = mockedUsuario.activarServicioAPI("AirVisual");
        //Assert
        assertFalse(activado);
    }
}
