package Geografico.model.API;

import Geografico.model.Equipo;

import java.util.List;

public interface APISportsDataInterface {
    public List<String> getLigas();
    public List<Equipo> getEquipos(String id);
    public String getClasificacion();
    public String getPartidosActuales();
    public Equipo getEquipo(String id);
    public boolean elegirLiga(String usuario, String liga);
    public List<String> getPartidosLiga(String liga);
}
