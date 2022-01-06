package Geografico.Aceptacion;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIHelper;
import Geografico.model.ListaUsuario;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestsAceptacion_H_2_4 {
    private Usuario usuario;
    private ListaUsuario listaUsuario;

    @BeforeEach
    public void setUp() throws SQLException {
        listaUsuario = new ListaUsuario();
        usuario = new Usuario("usuarioPruebas", "pass");
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

    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_1_SeMuestraLaInfoDeLaUbicacionSelecionada() throws AlreadyHasPlaceException, NotFoundPlaceException {
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        Ubicacion ubicacionAComprovar = usuario.altaUbicacionToponimo("Valencia");
        usuario.altaUbicacionToponimo("Alicante");
        //Act
        Ubicacion ubicacion = usuario.getUbicacion("Valencia");
        //Para simular el comportamiento de la app solo cogeremos la latitud y la temperatura actual de OpenWeather,
        //de esta ultima solo comprobaremos que no salta ninguna excepcion ya que no se puede asegurar el número exacto.
        usuario.activarServicioAPI(APIHelper.WEATHERAPI);
        usuario.altaServicioUbicacion(ubicacion, APIHelper.WEATHERAPI);
        double latitudRecivida = ubicacion.getLatitud();
        //Assert
        assertEquals(ubicacionAComprovar.getLatitud(), latitudRecivida);
        usuario.getWeather().get(ubicacion.getNombre()).gettActual();

    }

    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_1_SeNotificaQueLaAPINoEstaDisponible() throws AlreadyHasPlaceException {
        //Arrange
        usuario.altaUbicacionToponimo("Castellón");
        Ubicacion ubicacionAComprovar = usuario.altaUbicacionToponimo("Valencia");
        usuario.altaUbicacionToponimo("Alicante");
        //Act
        Ubicacion ubicacion = usuario.getUbicacion("Valencia");
        //Para simular el comportamiento de la app solo cogeremos la latitud y la temperatura actual de OpenWeather,
        //de esta ultima solo comprobaremos que salta NullPointerException ya que no se encuentra disponible al no estar
        //suscrito al servicio.
        double latitudRecivida = ubicacion.getLatitud();
        //Assert
        assertEquals(ubicacionAComprovar.getLatitud(), latitudRecivida);
        assertThrows(NullPointerException.class,
                ()-> usuario.getWeather().get(ubicacion.getNombre()).gettActual());
    }
}
