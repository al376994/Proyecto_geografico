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

public class TestsAceptacion_H_7_2 {
    private Usuario usuario;
    private DataBaseFunctions dataBaseFunctions;


    @BeforeEach
    private void iniciarVariables() throws SQLException {
        usuario = new Usuario();
        usuario.setNombre("usuarioFv");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    @Test
    public void desactivarLocacalizacionFavorita_E7_2_1_desactivadoCorrecto() throws SQLException, CoordenadasExcepcion {
        //Arrange
        List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);

        //Act
        Boolean anadido = dataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertEquals(true, anadido);
    }

    @Test
    public void desactivarLocacalizacionFavorita_E7_2_2_desactivadoIncorrecto() throws SQLException {
        //Arrange
        List<Ubicacion> listaUbicaciones = dataBaseFunctions.listarUbicacionesUsuario(usuario.getNombre());
        Ubicacion castellon = listaUbicaciones.get(0);
        //Act
        Boolean anadido = dataBaseFunctions.desactivarUbicacionFavorita(usuario.getNombre(), castellon.getNombre());
        //Assert
        assertEquals(false, anadido);
    }
}
