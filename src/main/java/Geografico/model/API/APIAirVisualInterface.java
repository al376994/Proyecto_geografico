package Geografico.model.API;

public interface APIAirVisualInterface {
	boolean validarToponimo(String toponimo);
	boolean validarToponimoExaustivo(String ciudad, String provincia, String pais);
}
