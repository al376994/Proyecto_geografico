package Geografico.Integracion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H1_10 {
    private Usuario mockedUsuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp() {
        mockedUsuario = Mockito.mock(Usuario.class);
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellón");
    }

    @Test
    public void darDeBajaUbicacionDisponible_E1_10_1_laUbicacionSeDaDeBaja() {
        //Arrange
        //Act
        when(mockedUsuario.darDeBajaUbicacion(ubicacion)).thenReturn(true);
        Boolean baja = mockedUsuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertTrue(baja);
    }

    @Test
    public void darDeBajaUbicacionNoDisponible_E1_10_2_laUbicacionNoSePuedeDarDeBaja() {
        //Arrange
        //Act
        when(mockedUsuario.darDeBajaUbicacion(ubicacion)).thenReturn(false);
        Boolean baja = mockedUsuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertFalse(baja);
    }
}
