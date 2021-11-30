package Geografico.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Ubicacion {
	private double latitud;
	private double longitud;
	private String nombre;
	private String alias;

	public Ubicacion(){
	}

	public Ubicacion(double latitud, double longitud, String nombre) {
		this.latitud = latitud;
		this.longitud = longitud;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public static Ubicacion crearUbicacionDesdeGeocode(JSONObject ubicacionSinProcesar) {
		Ubicacion ubicacion = new Ubicacion();
		try {
			//org.json.JSONException: No value for standard
			//ahora devuelvo city
			ubicacion.setLatitud(ubicacionSinProcesar.getDouble("latt"));
			ubicacion.setLongitud(ubicacionSinProcesar.getDouble("longt"));
			if(ubicacionSinProcesar.has("standard")) {//Si viene por toponimo el nombre esta dentro de standard
				ubicacion.setNombre(ubicacionSinProcesar.getJSONObject("standard").getString("city"));
			} else {//Si viene por coordenadas el nombre esta en la raiz
				ubicacion.setNombre(ubicacionSinProcesar.getString("city"));
			}

			System.out.println("\n\n\n" + ubicacionSinProcesar.toString() + "\n\n\n");
		} catch (JSONException e) {
			System.out.println("\n\n\n" + ubicacionSinProcesar.toString() + "\n\n\n");
			e.printStackTrace();
		}
		return ubicacion;
	}

	public Coordenadas getCoordenadas() {
		return new Coordenadas(getLatitud(), getLongitud());
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		setLatitud(coordenadas.getLatitud());
		setLongitud(coordenadas.getLongitud());
	}

	public void getInformacion() {
		//TODO cambiar para que devuelva String
	}
}
