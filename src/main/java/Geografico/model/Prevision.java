package Geografico.model;

import java.time.LocalDate;
import java.util.Date;

public class Prevision {
    private String tMax;
    private String tMin;
    private String humedad;
    private String descripcion;
    private Ciudad ciudad;
    private LocalDate date;

    public Prevision(String tMax, String tMin, String humedad, String descripcion, Ciudad ciudad) {
        this.tMax = tMax;
        this.tMin = tMin;
        this.humedad = humedad;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.date = java.time.LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
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
                ", ciudad=" + ciudad +
                ", date=" + date +
                '}';
    }
}
