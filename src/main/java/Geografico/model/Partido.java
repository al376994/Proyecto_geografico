package Geografico.model;

public class Partido {
    private Equipo equipoCasa;
    private Equipo equipoFuera;
    private String estadio;
    private String resultado;
    private String fecha;

    public Partido(Equipo equipoCasa, Equipo equipoFuera, String estadio, String resultado, String fecha) {
        this.equipoCasa = equipoCasa;
        this.equipoFuera = equipoFuera;
        this.estadio = estadio;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public Equipo getEquipoCasa() {
        return equipoCasa;
    }

    public void setEquipoCasa(Equipo equipoCasa) {
        this.equipoCasa = equipoCasa;
    }

    public Equipo getEquipoFuera() {
        return equipoFuera;
    }

    public void setEquipoFuera(Equipo equipoFuera) {
        this.equipoFuera = equipoFuera;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String toString(){
        return "El partido de " + equipoCasa.getNombre() + " contra " + equipoFuera.getNombre() + " ha quedado " + resultado
                + " del dia " + fecha;
    }
}
