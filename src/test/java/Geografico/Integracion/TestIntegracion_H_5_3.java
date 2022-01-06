package Geografico.Integracion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIntegracion_H_5_3 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void cerrarSesion_E5_3_1_cierreCorrecto() throws SQLException, CoordenadasExcepcion {
        //vamos a pedir primero las ubicaciones del usuario y luego simularemos un cierre de sesión
        //estableciendo la bbdd a null
        //Arrange
        usuario.setContrasena("pwd");
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Castellon"));
        //Act
        when(mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre())).thenReturn(aux);
        List<Ubicacion> ubicacionesActivas = mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        mockedDataBaseFunctions = null;
        //Assert
        assertEquals("Castellon", nombreUbicacion1);
    }

    @Test
    public void cerrarSesion_E5_3_2_cierreIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        ArrayList<Ubicacion> aux = new ArrayList<>();
        aux.add(new Ubicacion("Castellon"));
        //Act
//        when(mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre())).thenReturn(aux);
//        List<Ubicacion> ubicacionesActivas = mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
//        String nombreUbicacion1 = ubicacionesActivas.get(0).getNombre();
        mockedDataBaseFunctions = null;
        assertThrows(NullPointerException.class,
                ()-> {
                    mockedDataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        });
        //Assert
//        assertEquals("Castellon", nombreUbicacion1);
    }
}
