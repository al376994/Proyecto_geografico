package Geografico.model;

public class TiempoActual {
    private String tActual;
    private String sensacionTermica;
    private String humedad;
    private String presion;
    private String descripcion;

    public TiempoActual(String tActual, String sensacionTermica, String humedad, String presion, String d) {
        this.tActual = tActual;
        this.sensacionTermica = sensacionTermica;
        this.humedad = humedad;
        this.presion = presion;
        this.descripcion = d;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String gettActual() {
        return tActual;
    }

    public void settActual(String tActual) {
        this.tActual = tActual;
    }

    public String getSensacionTermica() {
        return sensacionTermica;
    }

    public void setSensacionTermica(String sensacionTermica) {
        this.sensacionTermica = sensacionTermica;
    }

    public String getHumedad() {
        return humedad;
    }

    public void setHumedad(String humedad) {
        this.humedad = humedad;
    }

    public String getPresion() {
        return presion;
    }

    public void setPresion(String presion) {
        this.presion = presion;
    }

    @Override
    public String toString() {
        return "TiempoActual{" +
                "tActual='" + tActual + '\'' +
                ", sensacionTermica='" + sensacionTermica + '\'' +
                ", humedad='" + humedad + '\'' +
                ", presion='" + presion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}