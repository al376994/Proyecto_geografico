package Geografico.model.API;

import Geografico.model.Equipo;
import Geografico.model.EquipoClasificacion;
import Geografico.model.Partido;
import org.json.JSONException;

import java.util.List;

public interface APISportsDataInterface {
    public List<String> getLigas();
    public Equipo getEquipo(String id) throws JSONException;
    public List<EquipoClasificacion> getClasificacionUsuario(String liga) throws JSONException;
    public List<EquipoClasificacion> getClasificacion(String id) throws JSONException;
    public List<EquipoClasificacion> getClasificacionChampions(String id) throws JSONException;
    public List<EquipoClasificacion> getClasificacionLaLiga(String id) throws JSONException;
    public boolean elegirLiga(String usuario, String liga);
    public boolean quitarLiga(String usuario, String liga);
    public List<Partido> getPartidosUsuario(String liga, boolean pasados) throws JSONException;
    public List<Partido> getPartidosLiga(String liga) throws JSONException;
    public String getSeason(String id);
}
