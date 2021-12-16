package Geografico.model.API;

import Geografico.model.Ciudad;
import Geografico.model.Prevision;
import Geografico.model.TiempoActual;
import org.json.JSONException;

import java.util.List;

public interface APIOpenWeatherInterface {
    public TiempoActual getTiempoActual (Ciudad c) throws JSONException;
    public List<Prevision> getPrevisionDiaria (Ciudad c) throws JSONException;
}
