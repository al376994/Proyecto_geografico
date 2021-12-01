package Geografico.Integracion;

import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestIntegracion_H_5_1 {
    private Usuario usuario;
    private DataBaseFunctions mockedDataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("nuevoUsuario");
        mockedDataBaseFunctions = Mockito.mock(DataBaseFunctions.class);
    }

    @Test
    public void registrarUsuario_E5_1_1_registroCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        ArrayList<String> mockedUsuarios = new ArrayList<>();
        mockedUsuarios.add("nuevoUsuario");
        usuario.setContrasena("superContrasenaSecreta");
        //Act
        when(mockedDataBaseFunctions.listarUsuarios()).thenReturn(mockedUsuarios);
        mockedDataBaseFunctions.addUsuario(usuario);
        List<String> usuarios = mockedDataBaseFunctions.listarUsuarios();
        //Assert
        assertEquals(true, usuarios.contains("nuevoUsuario"));
    }

    @Test
    public void registrarUsuario_E5_1_2_registroIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("");
        //Act
        doThrow(new IllegalArgumentException()).when(mockedDataBaseFunctions).addUsuario(usuario);
        assertThrows(IllegalArgumentException.class,
                ()->{
                    mockedDataBaseFunctions.addUsuario(usuario);
                });
    }
}
