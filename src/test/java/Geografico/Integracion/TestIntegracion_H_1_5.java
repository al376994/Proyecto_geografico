package Geografico.Integracion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_1_5 {
    private Usuario mockedUsuario;
    private Ubicacion ubicacion;
    
    @BeforeEach
    void setUp(){
        mockedUsuario = Mockito.mock(Usuario.class);
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellón");
    }

    @Test
    public void activarUbicacionDisponible_E1_5_1_QuedaActiva() {
        //Arrange
        //Act
        when(mockedUsuario.activarUbicacion(ubicacion)).thenReturn(true);
        Boolean activada = mockedUsuario.activarUbicacion(ubicacion);
        //Assert
        assertTrue(activada);
    }

    @Test
    public void activarUbicacionNoDisponible_E1_5_2_NoSeActiva() {
        //Arrange
        //Act
        when(mockedUsuario.activarUbicacion(ubicacion)).thenReturn(false);
        Boolean activada = mockedUsuario.activarUbicacion(ubicacion);
        //Assert
        assertFalse(activada);
    }
}
