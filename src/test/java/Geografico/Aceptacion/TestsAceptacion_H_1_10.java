package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_1_10 {
    private Usuario usuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castellón");
        ubicacion.setAlias("Castellon");
    }

    @Test
    public void darDeBajaUbicacionDisponible_E1_10_1_laUbicacionSeDaDeBaja() {
        //Arrange
        //Act
        Boolean baja = usuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertTrue(baja);
    }

    @Test
    public void darDeBajaUbicacionNoDisponible_E1_10_2_laUbicacionNoSePuedeDarDeBaja() {
        //Arrange
        //Act
        Boolean baja = usuario.darDeBajaUbicacion(ubicacion);
        //Assert
        assertFalse(baja);
    }
}
