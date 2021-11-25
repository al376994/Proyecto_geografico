package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H1_9 {
    private Usuario usuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellón");
    }

    @Test
    public void desactivarUbicacionActiva_E1_9_1_laUbicacionQuedaDesactivada() {
        //Arrange
        //Act
        boolean desactivada = usuario.desactivarUbicacion(ubicacion);
        //Assert
        assertTrue(desactivada);
    }

    @Test
    public void desactivarUbicacionYaDesactivada_E1_9_2_laUbicacionPermaneceDesactivada() {
        //Arrange
        //Act
        boolean desactivada = usuario.desactivarUbicacion(ubicacion);
        //Assert
        assertFalse(desactivada);
    }
}
