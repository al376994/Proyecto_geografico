package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.ListaUsuario;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestsAceptacion_H_5_1 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;
    private ListaUsuario listaUsuario;

    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("nuevoUsuario");
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
    public void registrarUsuario_E5_1_1_registroCorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pa$$word");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        List<String> usuarios = dataBaseFunctions.listarUsuarios();
        //Assert
        assertTrue(usuarios.contains("nuevoUsuario"));
    }

    @Test
    public void registrarUsuario_E5_1_2_registroIncorrecto() {
        //Arrange
        usuario.setContrasena("");
        //Act
        assertThrows(IllegalArgumentException.class,
                ()-> dataBaseFunctions.addUsuario(usuario));
    }
}