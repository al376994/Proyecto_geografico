package Geografico.Integracion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestIntegracion_H_5_2 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() {
        usuario = new Usuario();
        usuario.setNombre("usuarioIS");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void iniciarSesion_E5_2_1_inicioCorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        when(mockedDataBaseFunctions.iniciarSesion(usuario.getNombre(), usuario.getContrasena())).thenReturn(2);
        mockedDataBaseFunctions.addUsuario(usuario);
        int i = mockedDataBaseFunctions.iniciarSesion(usuario.getNombre(), usuario.getContrasena());
        //Assert
        assertEquals(2, i);
    }

    @Test
    public void iniciarSesion_E5_2_3_inicioIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        when(mockedDataBaseFunctions.iniciarSesion(usuario.getNombre(), "")).thenReturn(1);
        mockedDataBaseFunctions.addUsuario(usuario);
        int i = mockedDataBaseFunctions.iniciarSesion(usuario.getNombre(), "");
        //Assert
        assertEquals(1, i);
    }
}
