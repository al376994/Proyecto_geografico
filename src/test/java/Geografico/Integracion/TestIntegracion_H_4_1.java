package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_4_1 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBase;

    @BeforeEach
    public void setUp() {
        mockedDataBase = Mockito.mock(DataBaseFunctions.class);
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
    }

    @Test
    public void recuperarContenido_E4_1_1_seRecupera() throws SQLException {
        //Arrange
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Montanejos"));
        //Act
        when(mockedDataBase.listarUbicacionesUsuario("usuarioVersionAnterior")).thenReturn(aux);
        List<Ubicacion> ubicacionesActivas = usuario.getUbicaciones();
        String nombreUbicacion = ubicacionesActivas.get(0).getNombre();
        //Assert
        assertEquals("Montanejos", nombreUbicacion);
    }

    @Test
    public void recuperarContenido_E4_1_2_noSeRecupera() throws SQLException {
        //Arrange
        usuario.setNombre("usuarioNoGuardado");
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion(""));
        //Act
        when(mockedDataBase.listarUbicacionesUsuario("usuarioNoGuardado")).thenReturn(aux);
        List<Ubicacion> ubicacionesActivas = usuario.getUbicaciones();
        //Assert
        assertEquals(0, ubicacionesActivas.size());
    }
}
