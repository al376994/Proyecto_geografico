package Geografico.model;

import java.util.Locale;

public class Coordenadas {
	private double latitud;
	private double longitud;

	public Coordenadas(double latitud, double longitud) {
		this.latitud = latitud;
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public String getFormatoStringGeocode() {
		// El formato para que lea las coordenadas como tales es que: deben ser dos numeros con de
		// 4 a 6 decimales pegados por una ",", aqui nos aseguramos de que siempre tengasn 5 decimales
		// Locale.ENGLISH se usa para que al formatear la separación decimal se usen '.' en vez de ','
		return String.format(Locale.ENGLISH,"%.5f,%.5f", latitud, longitud);
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Coordenadas)) return false;
		if (this == object) return true;

		Coordenadas coordenadas = (Coordenadas) object;
		return this.latitud == coordenadas.latitud && this.longitud == coordenadas.longitud;
	}
}
