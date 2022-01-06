package Geografico.Integracion;

import Geografico.model.API.APIHelper;
import Geografico.model.TiempoActual;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TestIntegracion_H_2_4 {
    private Usuario mockedUsuario;
    private Ubicacion mockedubicacion;

    @BeforeEach
    public void setUp() {
        mockedUsuario = Mockito.mock(Usuario.class);
        mockedubicacion = Mockito.mock(Ubicacion.class);
    }

    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_1_SeMuestraLaInfoDeLaUbicacionSelecionada() throws AlreadyHasPlaceException, NotFoundPlaceException {
        //Arrange
        Ubicacion castellonWhen = new Ubicacion(39.97990, -0.03304, "Castellón");
        when(mockedUsuario.altaUbicacionToponimo("Castellón")).thenReturn(castellonWhen);
        when(mockedUsuario.getUbicacion("Castellón")).thenReturn(castellonWhen);
        Ubicacion valeciaWhen = new Ubicacion(39.50337, -0.40466, "Valencia");
        when(mockedUsuario.altaUbicacionToponimo("Valencia")).thenReturn(valeciaWhen);
        when(mockedUsuario.getUbicacion("Valencia")).thenReturn(valeciaWhen);

        Ubicacion castellonOriginal = mockedUsuario.altaUbicacionToponimo("Castellón");
        Ubicacion valeciaOriginal = mockedUsuario.altaUbicacionToponimo("Valencia");
        //Act
        Ubicacion castellonRecivido = mockedUsuario.getUbicacion("Castellón");
        Ubicacion valenciaRecivido = mockedUsuario.getUbicacion("Valencia");

        mockedUsuario.activarServicioAPI(APIHelper.WEATHERAPI);
        mockedUsuario.altaServicioUbicacion(castellonRecivido, APIHelper.WEATHERAPI);
        mockedUsuario.altaServicioUbicacion(valenciaRecivido, APIHelper.WEATHERAPI);
        TiempoActual tiempoActual = new TiempoActual("20", "", "", "", "");
        Map<String, TiempoActual> weather = Map.ofEntries(
                Map.entry("Castellón", tiempoActual),
                Map.entry("Valencia", tiempoActual)
        );
        when(mockedUsuario.getWeather()).thenReturn(weather);

        double latitudCastellonRecivida = castellonRecivido.getLatitud();
        double latitudValenciaRecivida = valenciaRecivido.getLatitud();
        String tActualCastellonRecivida = mockedUsuario.getWeather().get(castellonRecivido.getNombre()).gettActual();
        String tActualValenciaRecivida = mockedUsuario.getWeather().get(valenciaRecivido.getNombre()).gettActual();

        //Assert
        assertEquals(castellonOriginal.getLatitud(), latitudCastellonRecivida);
        assertEquals(valeciaOriginal.getLatitud(), latitudValenciaRecivida);

        assertEquals(tiempoActual.gettActual(), tActualCastellonRecivida);
        assertEquals(tiempoActual.gettActual(), tActualValenciaRecivida);
    }

    @Test
    public void consultarInformacionUbicacionesActivasPorSeparado_E2_4_2_SeNotificaQueLaAPINoEstaDisponible() throws AlreadyHasPlaceException {
        //Arrange
        Ubicacion castellonWhen = new Ubicacion(39.97990, -0.03304, "Castellón");
        when(mockedUsuario.altaUbicacionToponimo("Castellón")).thenReturn(castellonWhen);
        when(mockedUsuario.getUbicacion("Castellón")).thenReturn(castellonWhen);
        Ubicacion valeciaWhen = new Ubicacion(39.50337, -0.40466, "Valencia");
        when(mockedUsuario.altaUbicacionToponimo("Valencia")).thenReturn(valeciaWhen);
        when(mockedUsuario.getUbicacion("Valencia")).thenReturn(valeciaWhen);

        Ubicacion castellonOriginal = mockedUsuario.altaUbicacionToponimo("Castellón");
        Ubicacion valeciaOriginal = mockedUsuario.altaUbicacionToponimo("Valencia");
        //Act
        Ubicacion castellonRecivido = mockedUsuario.getUbicacion("Castellón");
        Ubicacion valenciaRecivido = mockedUsuario.getUbicacion("Valencia");

        TiempoActual tiempoActual = new TiempoActual("20", "", "", "", "");
        Map<String, TiempoActual> weather = Map.ofEntries();
        when(mockedUsuario.getWeather()).thenReturn(weather);

        double latitudCastellonRecivida = castellonRecivido.getLatitud();
        double latitudValenciaRecivida = valenciaRecivido.getLatitud();

        //Assert
        assertEquals(castellonOriginal.getLatitud(), latitudCastellonRecivida);
        assertEquals(valeciaOriginal.getLatitud(), latitudValenciaRecivida);

        assertThrows(NullPointerException.class,
                ()-> {String tiempo = mockedUsuario.getWeather().get(castellonRecivido.getNombre()).gettActual();});
        assertThrows(NullPointerException.class,
                ()-> {String tiempo = mockedUsuario.getWeather().get(valenciaRecivido.getNombre()).gettActual();});
    }
}
