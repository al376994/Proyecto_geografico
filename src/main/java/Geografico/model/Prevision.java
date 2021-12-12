package Geografico.model;

public class Prevision {
    private String tMax;
    private String tMin;
    private String humedad;
    private String descripcion;

    public Prevision(String tMax, String tMin, String humedad, String descripcion) {
        this.tMax = tMax;
        this.tMin = tMin;
        this.humedad = humedad;
        this.descripcion = descripcion;
    }

    public String gettMax() {
        return tMax;
    }

    public void settMax(String tMax) {
        this.tMax = tMax;
    }

    public String gettMin() {
        return tMin;
    }

    public void settMin(String tMin) {
        this.tMin = tMin;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Prevision{" +
                "tMax='" + tMax + '\'' +
                ", tMin='" + tMin + '\'' +
                ", humedad='" + humedad + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
