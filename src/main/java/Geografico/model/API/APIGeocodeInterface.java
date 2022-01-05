package Geografico.model.API;

import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import Geografico.model.excepciones.CoordenadasExcepcion;

public interface APIGeocodeInterface {
	boolean validarToponimo(String toponimo);
	boolean validarCoordenadas(double lat, double lon);
	Ubicacion getUbicacionToponimo(String toponimo);
	Ubicacion getUbicacionCoordenadas(double lat, double lon) throws CoordenadasExcepcion;
	Coordenadas getCoordenadasDeToponimo(String toponimo);
	String getDescripcion();
}
