package Geografico.Aceptacion;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestsAceptacion_H_5_4 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioCP");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    @Test
    public void modificarPerfil_E5_4_1_modificadoCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        int i = dataBaseFunctions.actualizarContraseña(usuario.getNombre(), usuario.getContrasena(),
                "nuevaPwd");
        //Assert
        assertEquals(1, i);
    }

    @Test
    public void modificarPerfil_E5_4_2_modificadoIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("pwd");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        int i = dataBaseFunctions.actualizarContraseña(usuario.getNombre(), usuario.getContrasena(),
                "");
        //Assert
        assertEquals(3, i);
    }
}
