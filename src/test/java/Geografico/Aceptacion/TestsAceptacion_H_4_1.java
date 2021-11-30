package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestsAceptacion_H_4_1 {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
    }

    @Test
    public void recuperarContenido_E4_1_1_seRecupera() throws SQLException {
        //Arrange
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicaciones();
        String nombreUbicacion = ubicacionesActivas.get(0).getNombre();
        //Assert
        assertEquals("Montanejos", nombreUbicacion);
    }

    @Test
    public void recuperarContenido_E4_1_2_noSeRecupera() throws SQLException {
        //Arrange
        usuario.setNombre("usuarioNoGuardado");
        //Act
        List<Ubicacion> ubicacionesActivas = usuario.getUbicaciones();
        //Assert
        assertEquals(0, ubicacionesActivas.size());
    }
}
