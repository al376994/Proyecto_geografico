package Geografico.model.API;

import Geografico.model.Polucion;
import Geografico.model.Ciudad;
import org.json.JSONException;

public interface APIAirVisualInterface {
	boolean validarToponimo(String toponimo);
	boolean validarToponimoExaustivo(String ciudad, String provincia, String pais);
	Ciudad getCiudadCercana() throws JSONException;
	Polucion getPolucionCiudadCercana() throws JSONException;
}
