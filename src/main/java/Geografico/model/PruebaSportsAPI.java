package Geografico.model;

import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIOpenDataSoft;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.API.APISportsData;
import org.json.JSONException;

import javax.servlet.http.Part;
import java.io.FileNotFoundException;
import java.util.List;

public class PruebaSportsAPI {
    public static void main(String[] args) throws JSONException, FileNotFoundException {
        DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
        APISportsData apiSportsData = new APISportsData();
//        List<Partido> partidos = apiSportsData.getPartidosUsuario("Bundesliga alemana");
//        for(Partido p: partidos){
//            dataBaseFunctions.anadirPartidoBundesliga(p);
//        }
//        APIAirVisual apiAirVisual = new APIAirVisual();
//        Polucion p = apiAirVisual.getPolucionCiudadCercana();
//        System.out.println(p.toString());;
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
