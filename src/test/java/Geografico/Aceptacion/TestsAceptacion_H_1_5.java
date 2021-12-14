package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_1_5 {
    private Usuario usuario;
    private Ubicacion ubicacion;

    @BeforeEach
    public void setUp(){
        usuario = new Usuario();
        ubicacion = new Ubicacion(39.97990, -0.03304, "Castello De La Plana");
        ubicacion.setAlias("Castellón");
    }

    @Test
    public void activarUbicacionDisponible_E1_5_1_QuedaActiva(){
        //Arrange
        //Act
        Boolean activada = usuario.activarUbicacion(ubicacion);
        //Assert
        assertTrue(activada);
    }

    @Test
    public void activarUbicacionNoDisponible_E1_5_2_NoSeActiva(){
        //Arrange
        //Act
        Boolean activada = usuario.activarUbicacion(ubicacion);
        //Assert
        assertFalse(activada);
    }
}
