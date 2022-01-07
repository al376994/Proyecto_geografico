package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_5_4 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;
    private ListaUsuario listaUsuario;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioCP");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        listaUsuario = new ListaUsuario();
        limpiarBaseDeDatos();
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), usuario.getContrasena()) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), usuario.getContrasena());
        }
    }

    @Test
    public void modificarPerfil_E5_4_1_modificadoCorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        int i = dataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "nuevaPwd");
        //Assert
        assertEquals(1, i);
    }

    @Test
    public void modificarPerfil_E5_4_3_modificadoIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        int i = dataBaseFunctions.actualizarContrasena(usuario.getNombre(), usuario.getContrasena(),
                "");
        //Assert
        assertEquals(3, i);
    }
}
