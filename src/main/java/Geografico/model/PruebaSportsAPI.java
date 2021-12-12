package Geografico.model;

import Geografico.model.API.APIOpenDataSoft;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.API.APISportsData;
import org.json.JSONException;

import java.io.FileNotFoundException;
import java.util.List;

public class PruebaSportsAPI {
    public static void main(String[] args) throws JSONException, FileNotFoundException {
        APISportsData apiSportsData = new APISportsData();
        APIOpenWeather apiOpenWeather = new APIOpenWeather();
        APIOpenDataSoft apiOpenDataSoft = new APIOpenDataSoft();
        apiOpenWeather.getPrevisionDiaria(new Ciudad("Valencia","Valencia",
                "Valencia","Valencia"));
//        List<Ciudad> ciudades = apiOpenDataSoft.getCapitales();
//        for (Ciudad c: ciudades){
//            apiOpenWeather.getTiempoActual(c);
//        }
//        System.out.println(apiSportsData.getEquipo("6402").toString());
//        apiSportsData.getClasificacion("2029");
//        apiSportsData.getPartidosLiga("2029");
//        List<EquipoClasificacion> clasificacion = apiSportsData.getClasificacionUsuario("La Liga Española");
//        for(EquipoClasificacion e:clasificacion){
//            System.out.println(e.toString());
//        }
//        List<Partido> partidos = apiSportsData.getPartidosUsuario("La Liga Española");
//        for(Partido p: partidos){
//            System.out.println(p.toString());
//        }

    }
}
