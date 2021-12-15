package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;

public class TestsAceptacion_H_2_4 {
    private List<Ubicacion> ubicacionesActivas;
    private Usuario usuario;
    private ListaUsuario listaUsuario;

    @BeforeEach
    public void setUp() throws SQLException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario("usuarioPruebas");
        limpiarBaseDeDatos();
        listaUsuario.addUsuario(usuario);
        // Esto, aunque redundante, sirve para simular el comportamiento completo del programa
        usuario = listaUsuario.getUsuario(usuario.getNombre(), null);
        usuario.addAPIGeocode(new APIGeocode());
    }

    @AfterEach
    private void limpiarBaseDeDatos() throws SQLException {
        if (listaUsuario.getUsuario(usuario.getNombre(), null) != null) {
            listaUsuario.deleteUsuario(usuario.getNombre(), null);
        }
    }

    //TODO CREO QUE ESTO NO ES EXACTAMENTE ASÍ. FALTA REVISAR
    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_1_SeMuestraLaInfoDeLaUbicacionSelecionada() throws SQLException, AlreadyHasPlaceException {
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        usuario.altaUbicacionToponimo("Valencia");
        usuario.altaUbicacionToponimo("Alicante");
        ubicacionesActivas = usuario.getUbicacionesActivas();
        //Act
        ubicacionesActivas.get(0).getInformacion();
        //Assert
    }

    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_1_SeNotificaQueLaAPINoEstaDisponible() throws SQLException {
        //Arrange
        ubicacionesActivas = usuario.getUbicacionesActivas();
        //Act
        ubicacionesActivas.get(0).getInformacion();
        //Assert
    }
}
