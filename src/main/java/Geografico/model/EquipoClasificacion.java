package Geografico.model;

public class EquipoClasificacion extends Equipo{
    private int puntos;
    private int partidosGanados;
    private int partidosPerdidos;
    private int partidosEmpatados;

    public EquipoClasificacion(String nombre, String pais, String id, String logo, int p, int pG, int pP, int pE) {
        super(nombre, pais, id, logo);
        this.puntos = p;
        this.partidosGanados = pG;
        this.partidosPerdidos = pP;
        this.partidosEmpatados = pE;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public int getPartidosEmpatados() {
        return partidosEmpatados;
    }

    public void setPartidosEmpatados(int partidosEmpatados) {
        this.partidosEmpatados = partidosEmpatados;
    }

    public String toString(){
        return "El equipo " + getNombre() + " de " + getPais() + " está con " + puntos + " puntos.";
    }
}
