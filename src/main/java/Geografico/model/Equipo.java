package Geografico.model;

public class Equipo {
    private String nombre;
    private String pais;
    private String id;

    public Equipo(String nombre, String pais, String id) {
        this.nombre = nombre;
        this.pais = pais;
        this.id = id;
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
}
