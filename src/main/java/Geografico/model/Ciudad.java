package Geografico.model;

public class Ciudad {
    private String nombre;
    private String provincia;
    private String pais;
    private String Comunidad;

    public Ciudad(String nombre, String provincia, String pais, String comunidad) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.pais = pais;
        this.Comunidad = comunidad;
    }

    public String getComunidad() {
        return Comunidad;
    }

    public void setComunidad(String comunidad) {
        Comunidad = comunidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String toString(){
        return nombre + " " + provincia + " " + Comunidad + " " + pais;
    }
}
