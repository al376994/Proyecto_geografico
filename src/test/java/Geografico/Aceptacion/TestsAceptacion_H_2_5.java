package Geografico.Aceptacion;

import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestsAceptacion_H_2_5 {
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
    }


    @Test
    public void reactivarUbicacionInactivaDelHistorial_E2_5_1_QuedaReactivada() {
        //Arrange
        //Act
        List<Ubicacion> historialUbicaciones = usuario.getHistorialUbicaciones();
        boolean reactivada = usuario.reactivarUbicacionDelHistorial(historialUbicaciones.get(0));
        //Assert
        assertTrue(reactivada);
    }

    @Test
    public void reactivarUbicacionActivaDelHistoail_E2_5_3_SeNotificaQueEsaYaEstabaActivada() {
        //Arrange
        //Act
        //Assert
    }
}
