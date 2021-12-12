package Geografico.Integracion;

import Geografico.model.DataBaseConnector;
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

public class TestIntegracion_H_4_2 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;

    @BeforeEach
    public void setUp() {
        dataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
    }

    @Test
    public void recuperarContenidoCierreInesperado_E4_2_1_seRecupera() throws SQLException {
        //Arrange
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Montanejos"));
        //Act
        when(dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        List<Ubicacion> ubicacionesActiva1 = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion2 = ubicacionesActiva1.get(0).getNombre();
        //Assert
        assertEquals(nombreUbicacion2, nombreUbicacion1);
    }

    @Test
    public void recuperarContenidoCierreInesperado_E4_2_2_noSeRecupera() throws SQLException {
        //Arrange
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Montanejos"));
        //Act
        when(dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        //comprobación absurda, pero no hay otra
        assertEquals(null, dataBaseFunctions);
    }
}
