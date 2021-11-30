package Geografico.model.API;

import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONObject;

import java.util.Locale;

public class APIGeocode implements APIGeocodeInterface{

	private final String key = APIHelper.geoCodeKey;

	@Override
	public boolean validarToponimo(String toponimo) {

		String URI = String.format("https://geocode.xyz/%s?geoit=json&auth=" + key, toponimo);
		System.out.println("Peticon: " + URI);
		String raw = APIHelper.getBody(URI);

		JSONObject jObject;
		try {
			jObject = new JSONObject(raw);
			System.out.println(jObject);
			return validarUbicacion(jObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean validarCoordenadas(double lat, double lon) {
		// Para pedir una localización a Geocode por coords o por toponimo se usa el mismo formato
		// pero para que lea las coordenadas como tales necesitan un formato especial.
		System.out.println(lat + " " + lon);
		return validarToponimo(coordFormater(lat, lon));
	}

	@Override
	public Ubicacion getUbicacionToponimo(String toponimo) {

		String URI = String.format("https://geocode.xyz/%s?geoit=json&auth=136807723723026938676x50228", toponimo);
		String raw = APIHelper.getBody(URI);

		JSONObject jObject;
		try {
			jObject = new JSONObject(raw);
			if (!validarUbicacion(jObject)) return null; // TODO cambiar a un throws Excepcion concreta?
			return Ubicacion.crearUbicacionDesdeGeocode(jObject);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Ubicacion getUbicacionCoordenadas(double lat, double lon) throws CoordenadasExcepcion{
		// Para pedir una localización a Geocode por coords o por toponimo se usa el mismo formato
		// pero para que lea las coordenadas como tales necesitan un formato especial.
		if ((lat > 90 || lat < -90) && (lon > 180 || lon < -180)) {
				throw new CoordenadasExcepcion(2);
		}
		if (lat > 90 || lat < -90) {
			throw new  CoordenadasExcepcion(0);
		}
		if (lon > 180 || lon < -180) {
			throw new  CoordenadasExcepcion(1);
		}
		return getUbicacionToponimo(coordFormater(lat, lon));
	}

	private boolean validarUbicacion(JSONObject jsonObject) {
		// Si Geocode no es capaz de encontrar una ubicación devolvera un json con el elemento
		// "error", por consiguiente si el json tiene este elemento es que la ubicación no existe
		return !jsonObject.has("error");
	}

	@Override
	public Coordenadas getCoordenadasDeToponimo(String toponimo) {
		Ubicacion ubicacion = getUbicacionToponimo(toponimo);
		if (ubicacion == null) return null;
		return ubicacion.getCoordenadas();
	}

	private String coordFormater(double lat, double lon) {
		// El formato para que lea las coordenadas como tales es que: deben ser dos numeros con de
		// 4 a 6 decimales pegados por una ",", aqui nos aseguramos de que siempre tengasn 5 decimales
		// Locale.ENGLISH se usa para que al formatear la separación decimal se usen '.' en vez de ','
		return String.format(Locale.ENGLISH,"%.5f,%.5f", lat, lon);
	}
}
