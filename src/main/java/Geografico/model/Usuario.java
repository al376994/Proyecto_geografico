package Geografico.model;

import Geografico.model.API.APIGeocodeInterface;

import java.util.List;

public class Usuario {
    private String nombre;
    private String email;
    private List<APIGeocodeInterface> listAPIGeocode;

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

    public void addAPIGeocode(APIGeocodeInterface apiGeocodeInterface){

    }

    public void altaUbicacionToponimo(String toponimo){

    }

    public void altaUbicacionCoordenadas(double x, double y ){

    }

    public List<Ubicacion> getUbicaciones(){
        return null;
    }
}
