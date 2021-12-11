package Geografico.model;

import Geografico.model.API.APISportsData;
import org.json.JSONException;

import java.util.List;

public class PruebaSportsAPI {
    public static void main(String[] args) throws JSONException {
        APISportsData apiSportsData = new APISportsData();
//        System.out.println(apiSportsData.getEquipo("6402").toString());
//        apiSportsData.getClasificacion("2029");
//        apiSportsData.getPartidosLiga("2029");
        List<Partido> partidos = apiSportsData.getPartidosUsuario("La Liga Española");
        for(Partido p: partidos){
            System.out.println(p.toString());
        }
    }
}
