package Geografico.model.API;

import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import Geografico.model.excepciones.CoordenadasExcepcion;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class APIGeocode implements APIGeocodeInterface{

	private final String key = APIHelper.geoCodeKey;

	@Override
	public boolean validarToponimo(String toponimo) {

		toponimo = normalizarToponimo(toponimo);
		toponimo = toponimo.replace(" ", "%20");

		String URI = String.format("https://geocode.xyz/%s?geoit=json&auth=" + key, toponimo);
		System.out.println("Peticon: " + URI);
		String raw = APIHelper.getBody(URI);

		JSONObject jObject;
		try {
			jObject = new JSONObject(raw);
			System.out.println(jObject);
			return validarUbicacion(jObject);
		} catch (JSONException e) {
			// Cuando la api geocode no encuentra una ubicacion para unas coordenadas validas no devuelve json,
			// por lo saltara la excepcion JSONException, como el funcionmiento final es el esperado (return false)
			// y no es trivial distinguir si JSONException a saltado por esta razón hemos decidido dejarlo como esta
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

		toponimo = normalizarToponimo(toponimo);
		toponimo = toponimo.replace(" ", "%20");

		String URI = String.format("https://geocode.xyz/%s?geoit=json&auth=" + key, toponimo);
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

	private String normalizarToponimo(String toponimo) {
		toponimo = toponimo.replace("á", "a");
		toponimo = toponimo.replace("à", "a");
		toponimo = toponimo.replace("é", "e");
		toponimo = toponimo.replace("è", "e");
		toponimo = toponimo.replace("í", "i");
		toponimo = toponimo.replace("ì", "i");
		toponimo = toponimo.replace("ó", "o");
		toponimo = toponimo.replace("ò", "o");
		toponimo = toponimo.replace("ú", "u");
		toponimo = toponimo.replace("ù", "u");
		return toponimo;
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
		if (!validarCoordenadas(lat, lon)) {
			throw new CoordenadasExcepcion(3);
		}
		// Hacemos esto porque al buscar por coordenadas y darte una ubicacion, las coordenadas que de la API son las
		// más cercanas a las coordenadas dadas dentro del área de la ubicacion en vez de las coordenadas centrales de
		// la ubicacion, que es lo que da buscar por toponimo, por lo tanto para evitar tener una ubicacion con
		// multiples coordenadas en la base de datos (podria causar algun problema) sacamos el toponimo de la busqueda
		// por coordenada y luego hacemos una busqueda por toponimo. Esta API no puede encontrar lugares que no tienen
		// toponimop así que no causa problemas en ese apartado este arreglo.
		String toponimo = getUbicacionToponimo(coordFormater(lat, lon)).getNombre();
		return getUbicacionToponimo(toponimo);
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

	public String getDescripcion(){
		return "API para obtener ubicaciones";
	}

	public String getPeticiones(){
		return "1 petición/segundo";
	}

	public List<String> getAtributos(){
		List<String> l = new ArrayList<>();
		l.add("Convierte coordenadas a topónimos y vicerversa");
		return l;
	}


	public String getNombre(){
		return "GeoCode";
	}
}
