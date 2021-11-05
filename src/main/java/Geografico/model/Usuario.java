package Geografico.model;

import Geografico.model.API.APIGeocodeInterface;

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

	public void altaUbicacionCoordenadas(double lat, double lon ){
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		if (nuevaUbicacion != null)guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
	}

	private void guardarUbicacionEnBaseDeDatos(Ubicacion ubicacion) {
		// TODO comunicar con la base de datos (guardar ubicación)
		dataBaseFunctions.añadirUbicacionUsuario(this.nombre, ubicacion.getLatitud(),
				ubicacion.getLongitud(), ubicacion.getAlias());
	}

	public List<Ubicacion> getUbicaciones() throws SQLException {
		// TODO comunicar con la base de datos (recibir ubicaciones)
		return dataBaseFunctions.listarUbicacionesUsuario(this.nombre);
	}
}
