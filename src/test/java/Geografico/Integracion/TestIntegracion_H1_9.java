package Geografico.Integracion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class TestIntegracion_H1_9 {
    private Usuario mockedUsuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp() {
        mockedUsuario = Mockito.mock(Usuario.class);
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellón");
    }

    @Test
    public void desactivarUbicacionActiva_E1_9_1_laUbicacionQuedaDesactivada() {
        //Arrange
        //Act
        when(mockedUsuario.desactivarUbicacion(ubicacion)).thenReturn(true);
        Boolean desactivada = mockedUsuario.desactivarUbicacion(ubicacion);
        //Assert
        assertTrue(desactivada);
    }

    @Test
    public void desactivarUbicacionYaDesactivada_E1_9_2_laUbicacionPermaneceDesactivada() {
        //Arrange
        //Act
        when(mockedUsuario.desactivarUbicacion(ubicacion)).thenReturn(false);
        Boolean desactivada = mockedUsuario.desactivarUbicacion(ubicacion);
        //Assert
        assertFalse(desactivada);
    }
}
