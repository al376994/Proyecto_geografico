package Geografico.model.API;

import Geografico.model.*;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
Documentación-> https://app.sportdataapi.com/documentation
ApiKey-> https://app.sportdataapi.com/api/v1/status?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460
Spain-> country_id:113
Liga española-> id:538-> season_id:2029
Champions League-> id:281-> season_id:1959
Bundesliga Alemana -> id:314 -> season_id:2020
Team_id example:6402
 */

public class APISportsData implements APISportsDataInterface{
    private List<String> ligas = new ArrayList<>();
    private DataBaseFunctions dataBaseFunctions;
    private String apiKey = "https://app.sportdataapi.com/api/v1/status?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460";

    public APISportsData(){
        ligas.add("La Liga Española");
        ligas.add("Bundesliga Alemana");
        dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    }

    //Devuelve las dos ligas para que el usuario elija
    @Override
    public List<String> getLigas() {
        return ligas;
    }

    //Inserta en la bbdd el usuario+liga o lo actualiza si ya estaba
    @Override
    public boolean elegirLiga(String usuario, String liga) {
        return dataBaseFunctions.anadirLigaUsuario(usuario, liga);
    }

    @Override
    public boolean quitarLiga(String usuario, String liga) {
        return dataBaseFunctions.eliminarLigaUsuario(usuario, liga);
    }

    //Le proporcionas el id del equipo y te devuelve un objeto Equipo con todo sus atributos
    @Override
    public Equipo getEquipo(String id) throws JSONException {
        String raw = APIHelper.getBody("https://app.sportdataapi.com/api/v1/soccer/teams/"+id+"?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460");

        JSONObject jsonObject = new JSONObject(raw);
//        System.out.println(jsonObject);
        JSONObject data = new JSONObject(jsonObject.getString("data"));
        JSONObject country = new JSONObject(data.getString("country"));
        String logo = data.getString("logo");
        return new Equipo(data.get("name").toString(),country.get("name").toString(),id, logo);
    }

    public List<Partido> getPartidosUsuario(String liga) throws JSONException {
        switch (liga){
            case "La Liga Española":
                return dataBaseFunctions.getPartidos();
            default:
                return dataBaseFunctions.getPartidosBundesliga();
        }
    }

    public List<EquipoClasificacion> getClasificacionUsuario(String liga) throws JSONException {
        switch (liga){
            case "La Liga Española":
                return getClasificacionLaLiga("2029");
            default:
                return getClasificacionLaLiga("2020");
        }
    }

    //Como el formato con el que devuelve la clasificacióne es diferente entre las ligas
    //Dependiendo del id de temporada (relacionado a la liga) que le demos nos redirigirá a uno o otro
    //Esto es transparente para el usuario
    public List<EquipoClasificacion> getClasificacion(String id) throws JSONException {
        switch (id){
            case "2029":
                return getClasificacionLaLiga(id);
            default:
                return getClasificacionLaLiga(id);
        }
    }

    //No hace falta
    public List<EquipoClasificacion> getClasificacionChampions(String id) throws JSONException {
        List<EquipoClasificacion> clasificacion = new ArrayList<>();
        return clasificacion;
    }

    //Proporcionando el id de la temporada, devuelve una lista de EquipoClasificacion
    //EquipoClasificacion(nombre, pais, logo, team_id, puntos, partidosGanados, partidosPerdidos, partidosEmpatados)
    @Override
    public List<EquipoClasificacion> getClasificacionLaLiga(String id) throws JSONException {
        List<EquipoClasificacion> clasificacion = new ArrayList<>();
        String raw = APIHelper.getBody("https://app.sportdataapi.com/api/v1/soccer/standings?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460&season_id="+id);
        JSONObject jsonObject = new JSONObject(raw);
        JSONObject data = new JSONObject(jsonObject.getString("data"));
        JSONArray standings = new JSONArray(data.getString("standings"));
        int i = 0;
        while (i < standings.length()){
            JSONObject aux = (JSONObject) standings.get(i);
            String team_id = aux.getString("team_id");
            Integer points = aux.getInt("points");
            JSONObject overall = new JSONObject(aux.getString("overall"));
            Integer pG = (Integer) overall.get("won");
            Integer pP = (Integer) overall.get("lost");
            Integer dw = (Integer) overall.get("draw");
            Equipo equipoSinClasificacion = getEquipo(team_id);
            EquipoClasificacion equipoClasificacion = new EquipoClasificacion(equipoSinClasificacion.getNombre(),
                    equipoSinClasificacion.getPais(), equipoSinClasificacion.getLogo(), team_id, points, pG, pP, dw);
//            System.out.println(equipoClasificacion.toString());
            clasificacion.add(equipoClasificacion);
            i++;
        }
        return clasificacion;
    }

    //Proporcionando el id de la temporada, nos devuelve una lista con los partidos de esta
    //Se puede configurar la fecha de inicio y de fin
    //Como la API está restriginda si no pagamos-> solo vamos a visualizar los partidos de la última jornada
    //Partido(equipoCasa, equipoVisitante, estadio, resultado, fecha)
    @Override
    public List<Partido> getPartidosLiga(String id) throws JSONException {
        List<Partido> partidos = new ArrayList<>();
        String raw = APIHelper.getBody("https://app.sportdataapi.com/api/v1/soccer/matches?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460&season_id="+id+"&date_from=2021-12-10");
//        System.out.println(raw);
        JSONObject obj = new JSONObject(raw);
//        System.out.println(raw);
        JSONArray data = new JSONArray(obj.getString("data"));
        int i = 0;
        while (i < data.length()){
            JSONObject match = (JSONObject) data.get(i);
//            System.out.println(match);
            try {
                JSONObject venue = new JSONObject(match.getString("venue"));
                if (venue != null){
//                System.out.println(match);
                    String estadio = new JSONObject(match.getString("venue")).getString("name");
                    Equipo awayTeam = getEquipo(new JSONObject(match.getString("away_team")).getString("team_id"));
                    Equipo homeTeam = getEquipo(new JSONObject(match.getString("home_team")).getString("team_id"));
                    String golesHome = new JSONObject(match.getString("stats")).getString("home_score");
                    String golesAway = new JSONObject(match.getString("stats")).getString("away_score");
                    String resultado = golesHome + " - " + golesAway;
                    String fecha = match.getString("match_start_iso").split("T")[0];
//            System.out.println(match);
                    System.out.println(awayTeam.getLogo());
                    Partido partido = new Partido(homeTeam, awayTeam, estadio, resultado, fecha);
//            System.out.println(partido.toString());
                    partidos.add(partido);
                }
            }catch (JSONException jsonException){
                i += 1;
            }
            i++;
        }
        return partidos;
    }
    //Proporcionando el id de la liga, devuelve los id's de las temporadas registradas.
    public String getSeason(String id){
        return APIHelper.getBody("https://app.sportdataapi.com/api/v1/soccer/seasons?apikey=5aa5db70-5b72-11ec-9e41-f38ffd713460&league_id="+id);
    }

    public String getDescripcion(){
        return "API para obtener la clasificación y los partidos de cualquier liga de fútbol";
    }

    public String getPeticiones(){
        return "500 peticiones/día";
    }

    public List<String> getAtributos(){
        List<String> l = new ArrayList<>();
        l.add("Clasificación de la liga");
        l.add("Partidos pasado y futuros");
        return l;
    }


    public String getNombre(){
        return "SporsData";
    }
}
