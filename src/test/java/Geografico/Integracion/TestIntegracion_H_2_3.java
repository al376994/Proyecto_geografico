package Geografico.Integracion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_2_3 {
    private Usuario mockedUsuario;
    private List<Ubicacion> ubicacionesActivas;

    @BeforeEach
    public void setUp() {
        mockedUsuario = Mockito.mock(Usuario.class);
        ubicacionesActivas = new ArrayList<Ubicacion>();
    }


    @Test
    public void consultarListaUbicacionesActivas_E2_3_1_seMuestraLaLista() {
        //Arrange
        Ubicacion ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellón");
        ubicacionesActivas.add(ubicacion);
        //Act
        when(mockedUsuario.getUbicacionesActivas()).thenReturn(ubicacionesActivas);
        List<Ubicacion> resultado = mockedUsuario.getUbicacionesActivas();
        //Assert
        assertEquals(1, resultado.size());
    }

    @Test
    public void consultarListaUbicacionesActivas_E2_3_2_noHayUbicacionesEnLaLista() {
        //Arrange
        //Act
        when(mockedUsuario.getUbicacionesActivas()).thenReturn(ubicacionesActivas);
        List<Ubicacion> resultado = mockedUsuario.getUbicacionesActivas();
        //Assert
        assertEquals(0, resultado.size());
    }
}
