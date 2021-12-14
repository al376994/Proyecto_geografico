package Geografico.model;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.excepciones.CoordenadasExcepcion;
import Geografico.model.excepciones.NotFoundPlaceException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private String email;
	private String contrasena;

	private List<APIGeocodeInterface> listAPIGeocode = new ArrayList<>();
	private APIGeocodeInterface apiGeocode; // La API de geolocalización elegida actualmente por el usuario
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

	public Ubicacion altaUbicacionToponimo(String toponimo){
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

	public Ubicacion altaUbicacionCoordenadas(double lat, double lon ) throws CoordenadasExcepcion {
		if (apiGeocode == null) addAPIGeocode(new APIGeocode());
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		if (nuevaUbicacion != null) guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
		return nuevaUbicacion;
	}

	public Ubicacion bajaUbicacionToponimo(String toponimo){
		Ubicacion nuevaUbicacion = dataBaseFunctions.getAddedUbicacionPorToponimo(toponimo);

		if ( nuevaUbicacion == null ) return null;
		dataBaseFunctions.quitarUbicacionUsuario(getNombre(), nuevaUbicacion.getNombre());
		return nuevaUbicacion;
	}

	private void guardarUbicacionEnBaseDeDatos(Ubicacion ubicacion) {
		// TODO comunicar con la base de datos (guardar ubicación)
		dataBaseFunctions.añadirUbicacionUsuario(this.nombre, ubicacion.getLatitud(),
				ubicacion.getLongitud(), ubicacion.getNombre(), ubicacion.getAlias());
	}

	public List<Ubicacion> getUbicaciones() throws SQLException {
		// TODO comunicar con la base de datos (recibir ubicaciones)
		return dataBaseFunctions.listarUbicacionesUsuario(this.nombre);
	}

	public boolean activarUbicacion(Ubicacion ubicacion) {
		dataBaseFunctions.activarUbicacion(this.nombre, ubicacion);
		return dataBaseFunctions.isLocationActive(this.nombre, ubicacion);
	}

	public boolean desactivarUbicacion(Ubicacion ubicacion) {
		dataBaseFunctions.desactivarUbicacion(this.nombre, ubicacion);
		return dataBaseFunctions.isLocationActive(this.nombre, ubicacion);
	}

	public Boolean darDeBajaUbicacion(Ubicacion ubicacion) {
		//TODO comprobar que existe esa ubicacion y eliminarla y devolver true o folse si se ha podido aliminar
		return true;
	}

	public String obtenerToponimoCercanoCoordenadas(double lat, double lon) throws CoordenadasExcepcion {
		String toponimo = apiGeocode.getUbicacionCoordenadas(lat, lon).getNombre();
		return toponimo;
	}

	public List<Ubicacion> getUbicacionesActivas() throws SQLException {
		return dataBaseFunctions.getUbicacionesActivas(this.nombre);
	}

	public boolean asignarAliasUbicacion(String ubicacion, String alias){
		if (alias.equals("")){
			throw new IllegalArgumentException();
		}
		return dataBaseFunctions.altaAliasUbicacion(nombre, ubicacion, alias);
	}

	public Ubicacion getUbicacion(String ubicacion){
		return dataBaseFunctions.getUbicacionUsuario(getNombre(), ubicacion);
	}

	public String getAliasUbicacion(String ubicacion){
		return dataBaseFunctions.getAliasUbicacion(nombre, ubicacion);
	}

    public List<Ubicacion> getHistorialUbicaciones() {
		//TODO devolver listado de ubiaciones en el historial de un usuario
		List<Ubicacion> historialUbicaciones = new ArrayList<>();
		return historialUbicaciones;
    }

	public boolean reactivarUbicacionDelHistorial(Ubicacion ubicacion) {
		//TODO para reactivar eliminas la ubicacion del historial y la agregas a la tabla de usuario_ubicaciones de nuevo
		return true;
	}

	public List<String> getServiciosAPIDisponibles() {
		List<String> APIsDisponibles = dataBaseFunctions.getAPIsDisponibles();
		return APIsDisponibles;
	}

	public boolean activarServicioAPI(String servicio) {
		try {
			dataBaseFunctions.activarServicioAPI(this.nombre, servicio);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean desactivarServicioAPI(String servicio) {
		try {
			dataBaseFunctions.desactivarServicioAPI(this.nombre, servicio);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
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
}
