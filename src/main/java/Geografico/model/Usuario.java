package Geografico.model;

import Geografico.model.API.*;
import Geografico.model.excepciones.AirPolutionAPIRequestsLimitReachedException;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.CoordenadasExcepcion;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
	private String nombre;
	private String email;
	private String contrasena;

	private final List<APIGeocodeInterface> listAPIGeocode = new ArrayList<>();
	private APIGeocodeInterface apiGeocode; // La API de geolocalización elegida actualmente por el usuario
	private final APIOpenWeatherInterface apiOpenWeather = new APIOpenWeather();
	private final APIAirVisualInterface apiAirVisual = new APIAirVisual();
	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	// FIN DE VARIABLES

	public Usuario() {
	}

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

	public Usuario(String nombre, String pwd){
		this.nombre = nombre;
		this.contrasena = pwd;
	}

	// FIN DE CONSTRUCTORES

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	// FIN DE SETTERS Y GETTERS

	public void setDataBaseFunctions(DataBaseFunctions dataBaseFunctions) {
		this.dataBaseFunctions = dataBaseFunctions;
	}

	public void addAPIGeocode(APIGeocodeInterface apiGeocode){
		if (listAPIGeocode.isEmpty()) this.apiGeocode = apiGeocode;
		listAPIGeocode.add(apiGeocode);
	}

	public Ubicacion altaUbicacionToponimo(String toponimo) throws AlreadyHasPlaceException {
		Ubicacion nuevaUbicacion = dataBaseFunctions.getAddedUbicacionPorToponimo(toponimo);
		if (apiGeocode == null) addAPIGeocode(new APIGeocode());
		// Primero comprobamos si ya hemos registrado esta ubicacion anteriormente, si es el caso la sacamos de la
		// base de datos, si no la buscamos mediante la API.
		if ( nuevaUbicacion == null )nuevaUbicacion =
				apiGeocode.getUbicacionToponimo(toponimo);

		// Si no encuentra la ubicacion ni en la base de datos ni mediante la api entonces no se podrá guardar
		if ( nuevaUbicacion != null ) guardarUbicacionEnBaseDeDatos(nuevaUbicacion);

		return nuevaUbicacion;
	}

	public Ubicacion altaUbicacionCoordenadas(double lat, double lon ) throws CoordenadasExcepcion, AlreadyHasPlaceException {
		if (apiGeocode == null) addAPIGeocode(new APIGeocode());
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		if (nuevaUbicacion != null) guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
		return nuevaUbicacion;
	}

	public boolean darDeBajaUbicacion(Ubicacion ubicacion) {
		Ubicacion ubicacionADarDeBaja = dataBaseFunctions.getAddedUbicacionPorToponimo(ubicacion.getNombre());

		if ( ubicacionADarDeBaja == null ) return false;
		//TODO deberia saltar excepcion en el if ya que mediante interfaz no deberia de ocurrir este caso

		return dataBaseFunctions.quitarUbicacionUsuario(getNombre(), ubicacionADarDeBaja.getNombre());
	}

	private void guardarUbicacionEnBaseDeDatos(Ubicacion ubicacion) throws AlreadyHasPlaceException {
		dataBaseFunctions.addUbicacionUsuario(this.nombre, ubicacion);
	}

	public List<Ubicacion> getUbicaciones() throws SQLException {
		return dataBaseFunctions.listarUbicacionesUsuario(this.nombre);
	}

	public List<Ubicacion> getUbicacionesDesactivadas() throws SQLException {
		return dataBaseFunctions.getUbicacionesDesactivadasUsuario(this.nombre);
	}

	public Ubicacion getUbicacion(String ubicacion){
		return dataBaseFunctions.getUbicacionUsuario(getNombre(), ubicacion);
	}

	public boolean activarUbicacion(Ubicacion ubicacion) {
		dataBaseFunctions.activarUbicacion(this.nombre, ubicacion);
		return dataBaseFunctions.isLocationActive(this.nombre, ubicacion);
	}

	public boolean desactivarUbicacion(Ubicacion ubicacion) {
		dataBaseFunctions.desactivarUbicacion(this.nombre, ubicacion);
		return dataBaseFunctions.isLocationActive(this.nombre, ubicacion);
	}

	public List<Ubicacion> getUbicacionesActivas() throws SQLException {
		return dataBaseFunctions.getUbicacionesActivas(this.nombre);
	}

	public boolean toggleFavoritoUbicacion(Ubicacion ubicacion) throws SQLException {
		if (getUbicacion(ubicacion.getNombre()).isFavorito()) {
			dataBaseFunctions.desactivarUbicacionFavorita(nombre, ubicacion.getNombre());
			return false;
		} else {
			dataBaseFunctions.anadirUbicacionFavorita(nombre, ubicacion.getNombre());
			return true;
		}
	}

	public String obtenerToponimoCercanoCoordenadas(double lat, double lon) throws CoordenadasExcepcion {
		String toponimo = apiGeocode.getUbicacionCoordenadas(lat, lon).getNombre();
		return toponimo;
	}

	public boolean asignarAliasUbicacion(String ubicacion, String alias){
		if (alias.equals("")){
			throw new IllegalArgumentException();
		}
		return dataBaseFunctions.altaAliasUbicacion(nombre, ubicacion, alias);
	}

	public String getAliasUbicacion(String ubicacion){
		return dataBaseFunctions.getAliasUbicacion(nombre, ubicacion);
	}

    public List<Ubicacion> getHistorialUbicaciones() {
		List<Ubicacion> historialUbicaciones = dataBaseFunctions.getHistorialUbicaciones(this.nombre);
		return historialUbicaciones;
    }

	public boolean reactivarUbicacionDelHistorial(String toponimo) {
		return dataBaseFunctions.reactivarUbicacion(this.nombre, toponimo);
	}

	public List<String> getServiciosAPIDisponibles() {
		List<String> APIsDisponibles = dataBaseFunctions.getAPIsDisponibles();
		return APIsDisponibles;
	}

	public boolean activarServicioAPI(String servicio) {
		try {
			dataBaseFunctions.activarServicioAPIUsuario(this.nombre, servicio);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean desactivarServicioAPI(String servicio) {
		try {
			dataBaseFunctions.desactivarServicioAPIUsuario(this.nombre, servicio);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<String> getServiciosAPI() {
		return dataBaseFunctions.getServiciosAPIUsuario(this.nombre);
	}

	public List<String> altaServicioUbicacion(Ubicacion ubicacion, String servicio) throws NotFoundPlaceException {
		dataBaseFunctions.altaServicioUbicacion(getNombre(), ubicacion.getNombre(), servicio);
		return getServiciosAPIUbicacion(ubicacion);
	}

	public List<String> bajaServicioUbicacion(Ubicacion ubicacion, String servicio) {
		dataBaseFunctions.bajaServicioUbicacion(getNombre(), ubicacion.getNombre(), servicio);
		return getServiciosAPIUbicacion(ubicacion);
	}

	public List<String> getServiciosAPIUbicacion(Ubicacion ubicacion) {
		return dataBaseFunctions.getServiciosAPIUbicacionUsuario(getNombre(), ubicacion.getNombre());
	}

	public List<Ubicacion> getUbicacionesConServicioAPI(String servicio) {
		return dataBaseFunctions.getUbicacionesConServicioUsuario(this.nombre, servicio);
	}

	public Map<String, TiempoActual> getWeather() {
		Map<String, TiempoActual> weather = new HashMap<>();
		List<Ubicacion> ubicaciones = getUbicacionesConServicioAPI(APIHelper.WEATHERAPI);
		for (Ubicacion ubicacion : ubicaciones) {
			try {
				weather.put(ubicacion.getNombre(), apiOpenWeather.getTiempoActual(ubicacion));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return weather;
	}

	public Map<String, Polucion> getAirPolution() {
		Map<String, Polucion> airPolution = new HashMap<>();
		List<Ubicacion> ubicaciones = getUbicacionesConServicioAPI(APIHelper.AIRPOLUTIONAPI);
		for (Ubicacion ubicacion : ubicaciones) {
			try {
				airPolution.put(ubicacion.getNombre(), apiAirVisual.getPolucionUbicacion(ubicacion));
			} catch (AirPolutionAPIRequestsLimitReachedException e) {
				airPolution.put(ubicacion.getNombre(), new Polucion(e.getMessage()));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return airPolution;
	}
}
