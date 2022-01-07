package Geografico.Integracion;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class TestIntegracion_H_5_4 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() {
        usuario = new Usuario();
        usuario.setNombre("usuarioCP");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void modificarPerfil_E5_4_1_modificadoCorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        when(mockedDataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "nuevaPwd")).thenReturn(1);
        mockedDataBaseFunctions.addUsuario(usuario);
        int i = mockedDataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "nuevaPwd");
        //Assert
        assertEquals(1, i);
    }

    @Test
    public void modificarPerfil_E5_4_3_modificadoIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        when(mockedDataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "")).thenReturn(3);
        mockedDataBaseFunctions.addUsuario(usuario);
        int i = mockedDataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "");
        //Assert
        assertEquals(3, i);
    }
}
