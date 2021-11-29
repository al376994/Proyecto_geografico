package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestsAceptacion_H_2_3 {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
    }

    @Test
    public void consultarListaUbicacionesActivas_E2_3_1_seMuestraLaLista() {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicacionesActivas();
        //Assert
        assertEquals(1, ubicacionesActivas.size());
    }

    @Test
    public void consultarListaUbicacionesActivas_E2_3_2_noHayUbicacionesEnLaLista() {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicacionesActivas();
        //Assert
        assertEquals(0, ubicacionesActivas.size());
    }
}
