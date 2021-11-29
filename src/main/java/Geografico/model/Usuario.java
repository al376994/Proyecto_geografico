package Geografico.model;

import Geografico.model.API.APIGeocodeInterface;
import Geografico.model.excepciones.CoordenadasExcepcion;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private String email;
	private List<APIGeocodeInterface> listAPIGeocode = new ArrayList<>();
	private APIGeocodeInterface apiGeocode; // La API de geolocalización elegida actualmente por el usuario
	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	public Usuario() {
	}

	public Usuario(String nombre) {
		this.nombre = nombre;
	}

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

	public void setDataBaseFunctions(DataBaseFunctions dataBaseFunctions) {
		this.dataBaseFunctions = dataBaseFunctions;
	}

	public void addAPIGeocode(APIGeocodeInterface apiGeocode){
		if (listAPIGeocode.isEmpty()) this.apiGeocode = apiGeocode;
		listAPIGeocode.add(apiGeocode);
	}

	public void altaUbicacionToponimo(String toponimo){
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionToponimo(toponimo);
		if (nuevaUbicacion != null)guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
	}

	public void altaUbicacionCoordenadas(double lat, double lon ) throws CoordenadasExcepcion {
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		if (nuevaUbicacion != null)guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
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
		//TODO comprobar que la ubicación está en la base de datos y esta desactivada, entonces devolver true si se activa o false si no.
		return true;
	}

	public boolean desactivarUbicacion(Ubicacion ubicacion) {
		//TODO comprobar que la ubicacion está en la base de datos y esta activada, entonces devovler true si se desactiva o false si no.
		return true;
	}

	public Boolean darDeBajaUbicacion(Ubicacion ubicacion) {
		//TODO comprobar que existe esa ubicacion y eliminarla y devolver true o folse si se ha podido aliminar
		return true;
	}

	public Ubicacion obtenerToponimoCercanoCoordenadas(double lat, double lon) throws CoordenadasExcepcion {
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		return nuevaUbicacion;
	}

	public List<Ubicacion> getUbicacionesActivas() throws SQLException {
		//TODO consulta que devuelva lista ubicaciones activas
		return dataBaseFunctions.getUbicacionesActivas(this.nombre);
	}

	public void asignarAliasUbicacion(String ubicacion, String alias){
		dataBaseFunctions.altaAliasUbicacion(nombre, ubicacion, alias);
	}

	public String getAliasUbicacion(String ubicacion){
		return dataBaseFunctions.getAliasUbicacion(nombre, ubicacion);
	}
}
