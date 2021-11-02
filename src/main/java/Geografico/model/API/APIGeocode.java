package Geografico.model.API;

import Geografico.model.Ubicacion;
import org.json.JSONObject;

public class APIGeocode implements APIGeocodeInterface{

	@Override
	public boolean validarToponimo(String toponimo) {

		String URI = String.format("https://geocode.xyz/%s?geoit=json&auth=136807723723026938676x50228", toponimo);
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
			double lat = Double.parseDouble(jObject.getString("latt"));
			double lon = Double.parseDouble(jObject.getString("longt"));
			return getUbicacionCoordenadas(lat, lon);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Ubicacion getUbicacionCoordenadas(double lat, double lon) {
		// Para pedir una localización a Geocode por coords o por toponimo se usa el mismo formato
		// pero para que lea las coordenadas como tales necesitan un formato especial.
		return getUbicacionToponimo(coordFormater(lat, lon));
	}

	private boolean validarUbicacion(JSONObject jsonObject) {
		// Si Geocode no es capaz de encontrar una ubicación devolvera un json con el elemento
		// "error", por consiguiente si el json tiene este elemento es que la ubicación no existe
		return !jsonObject.has("error");
	}

	private String coordFormater(double lat, double lon) {
		// El formato para que lea las coordenadas como tales es que: deben ser dos numeros con de
		// 4 a 6 decimales pegados por una ",", aqui nos aseguramos de que siempre tengasn 5 decimales
		return String.format("%.5f,%.5f", lat, lon);
	}
}
