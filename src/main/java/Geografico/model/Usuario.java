package Geografico.model;

import Geografico.model.API.APIGeocodeInterface;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nombre;
	private String email;
	private List<APIGeocodeInterface> listAPIGeocode = new ArrayList<>();
	private APIGeocodeInterface apiGeocode; // La API de geolocalización elegida actualmente por el usuario

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

	public void addAPIGeocode(APIGeocodeInterface apiGeocode){
		if (listAPIGeocode.isEmpty()) this.apiGeocode = apiGeocode;
		listAPIGeocode.add(apiGeocode);
	}

	public void altaUbicacionToponimo(String toponimo){
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionToponimo(toponimo);
		guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
	}

	public void altaUbicacionCoordenadas(double lat, double lon ){
		Ubicacion nuevaUbicacion = apiGeocode.getUbicacionCoordenadas(lat, lon);
		guardarUbicacionEnBaseDeDatos(nuevaUbicacion);
	}

	private void guardarUbicacionEnBaseDeDatos(Ubicacion ubicacion) {
		// TODO comunicar con la base de datos (guardar ubicación)
	}

	public List<Ubicacion> getUbicaciones(){
		// TODO comunicar con la base de datos (recibir ubicaciones)
		System.out.println("usuario.getUbicaciones() no implementado");
		return null;
	}
}
