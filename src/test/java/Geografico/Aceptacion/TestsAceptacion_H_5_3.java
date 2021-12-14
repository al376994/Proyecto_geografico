package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_5_3 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    @Test
    public void cerrarSesion_E5_3_1_cierreCorrecto() throws SQLException, CoordenadasExcepcion {
        //vamos a pedir primero las ubicaciones del usuario y luego simularemos un cierre de sesión
        //estableciendo la bbdd a null
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        assertEquals("Castello De La Plana", nombreUbicacion1);
    }

    @Test
    public void cerrarSesion_E5_3_2_cierreIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        List<Ubicacion> ubicacionesActivas = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        dataBaseFunctions = null;
        //Assert
        assertEquals(null, dataBaseFunctions);
    }
}
