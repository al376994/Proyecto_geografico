package Geografico.Aceptacion;

import Geografico.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestsAceptacion_H_3_1 {
    private Usuario usuario;


    @BeforeEach
    public void setUp() {
        usuario = new Usuario("NuevoUser");
    }

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_1_SeMuestranLosDisponibles() {
        //Arrange

        //Act
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        //Assert
        assertEquals(3, APIsDisponibles.size());
    }

    @Test
    public void consultarServiciosAPIDisponibles_E3_1_2_SeNotificaQueNoHayDisponibles() {
        //Arrange
        //Act
        List<String> APIsDisponibles= usuario.getServiciosAPIDisponibles();
        //Assert
        assertEquals(0, APIsDisponibles.size());
    }
}
