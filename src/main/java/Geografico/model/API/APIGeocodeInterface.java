package Geografico.model.API;

public interface APIGeocodeInterface {
    boolean validarToponimo(String toponimo);
    boolean validarCoordenadas(double lat, double lon);
    String getUbicacionToponimo(String toponimo);
    String getUbicacionCoordenadas(double lat, double lon);
}
