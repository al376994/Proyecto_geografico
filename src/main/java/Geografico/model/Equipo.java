package Geografico.model;

public class Equipo {
    private String nombre;
    private String pais;
    private String id;
    private String logo;

    public Equipo(){}
    public Equipo(String nombre, String pais, String id, String logo) {
        this.nombre = nombre;
        this.pais = pais;
        this.id = id;
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return "Equipo con nombre " + nombre + " del pais " + pais;
    }
}
