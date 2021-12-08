package Geografico.model.API;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Equipo;

import java.util.ArrayList;
import java.util.List;

public class APISportsData implements APISportsDataInterface{
    private List<String> ligas = new ArrayList<>();
    private DataBaseFunctions dataBaseFunctions;
    private String apiKey = "https://app.sportdataapi.com/api/v1/status?apikey=98b16990-2851-11ec-9a49-09d919d0ef27";

    public APISportsData(){
        ligas.add("La Liga Española"); //id 538
        ligas.add("UEFA Champions League"); //281
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    @Override
    public List<String> getLigas() {
        return ligas;
    }

    @Override
    public List<Equipo> getEquipos(String id) {
        
        return null;
    }

    @Override
    public String getClasificacion() {
        return null;
    }

    @Override
    public String getPartidosActuales() {
        return null;
    }

    @Override
    public Equipo getEquipo(String id) {
        return null;
    }

    @Override
    public boolean elegirLiga(String usuario, String liga) {
        return dataBaseFunctions.anadirLigaUsuario(usuario, liga);
    }

    @Override
    public List<String> getPartidosLiga(String liga) {
        List<String> partidos = new ArrayList<>();

        return partidos;
    }
}
