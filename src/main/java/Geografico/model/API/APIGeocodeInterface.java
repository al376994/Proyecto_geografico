package Geografico.model.API;

import Geografico.model.Ubicacion;

public interface APIGeocodeInterface {
	boolean validarToponimo(String toponimo);
	boolean validarCoordenadas(double lat, double lon);
	Ubicacion getUbicacionToponimo(String toponimo);
	Ubicacion getUbicacionCoordenadas(double lat, double lon);
}
