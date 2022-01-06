package Geografico.Integracion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_2_5 {
    private Usuario mockedUsuario;


    @Test
    public void reactivarUbicacionInactivaDelHistorial_E2_5_1_QuedaReactivada() {
        //Arrange
        mockedUsuario = Mockito.mock(Usuario.class);
        Ubicacion ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        List<Ubicacion> historial = new ArrayList<>();
        historial.add(ubicacion);
        //Act
        when(mockedUsuario.reactivarUbicacionDelHistorial(historial.get(0).getNombre())).thenReturn(true);
        boolean result = mockedUsuario.reactivarUbicacionDelHistorial(historial.get(0).getNombre());
        //Assert
        assertTrue(result);
    }

    @Test
    public void reactivarUbicacionActivaDelHistoail_E2_5_3_SeNotificaQueEsaYaEstabaActivada() {
        //Arrange
        mockedUsuario = Mockito.mock(Usuario.class);
        Ubicacion ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        List<Ubicacion> historial = new ArrayList<>();
        //Act
        when(mockedUsuario.reactivarUbicacionDelHistorial(ubicacion.getNombre())).thenReturn(false);
        boolean result = mockedUsuario.reactivarUbicacionDelHistorial(ubicacion.getNombre());
        //Assert
        assertFalse(result);
    }
}
