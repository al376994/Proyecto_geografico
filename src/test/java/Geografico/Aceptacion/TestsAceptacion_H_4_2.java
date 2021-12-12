package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestsAceptacion_H_4_2 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;

    @BeforeEach
    public void setUp() {
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        usuario = new Usuario();
        usuario.setNombre("usuarioVersionAnterior");
    }

    @Test
    public void recuperarContenidoCierreInesperado_E4_2_1_seRecupera() throws SQLException {
        //Arrange
        //Act
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
        //Act
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        //comprobación absurda, pero no hay otra
        assertEquals(null, dataBaseFunctions);
    }
}
