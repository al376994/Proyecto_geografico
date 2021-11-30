package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_5_1 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("nuevoUsuario");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    @Test
    public void registrarUsuario_E5_1_1_registroCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        usuario.setContrasena("superContrasenaSecreta");
        //Act
        dataBaseFunctions.addUsuario(usuario);
        List<String> usuarios = dataBaseFunctions.listarUsuarios();
        //Assert
        assertEquals(true, usuarios.contains("nuevoUsuario"));
    }

    @Test
    public void registrarUsuario_E5_1_2_registroIncorrecto() throws SQLException {
        //Arrange
        usuario.setContrasena("");
        //Act
        assertThrows(IllegalArgumentException.class,
                ()->{
                    dataBaseFunctions.addUsuario(usuario);
        });
    }
}
