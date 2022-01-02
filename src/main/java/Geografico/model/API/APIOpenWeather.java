package Geografico.model.API;

import Geografico.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class APIOpenWeather implements APIOpenWeatherInterface{
    private String apiKey = "2ec35783959b4041396cb606354134ff";

    public APIOpenWeather(){}

    public TiempoActual getTiempoActual (Ciudad c) throws JSONException {
//        System.out.println(c.getNombre());
        String raw = APIHelper.getBody("https://api.openweathermap.org/data/2.5/weather?q="
                + c.getNombre() + "&appid=" + apiKey + "&units=metric" + "&lang=es");
        JSONObject obj = new JSONObject(raw);
        JSONObject main = new JSONObject(obj.getString("main"));
        String temp =  main.getString("temp");
        String humedad = main.getString("humidity");
        String presion = main.getString("pressure");
        String sensacion = main.getString("feels_like");
        JSONArray weather = obj.getJSONArray("weather");
        JSONObject desc = new JSONObject(weather.getString(0));
        String descripcion = desc.getString("description");
        TiempoActual tiempoActual = new TiempoActual(temp, sensacion, humedad, presion, descripcion);
//        System.out.println(tiempoActual.toString());
        return tiempoActual;
    }

    public TiempoActual getTiempoActual (Ubicacion u) throws JSONException {
        String raw = APIHelper.getBody("https://api.openweathermap.org/data/2.5/weather?" +
                "lat=" + u.getLatitud() + "&lon=" + u.getLongitud() +
                "&appid=" + apiKey + "&units=metric" + "&lang=es");
        JSONObject obj = new JSONObject(raw);
        JSONObject main = new JSONObject(obj.getString("main"));
        String temp =  main.getString("temp");
        String humedad = main.getString("humidity");
        String presion = main.getString("pressure");
        String sensacion = main.getString("feels_like");
        JSONArray weather = obj.getJSONArray("weather");
        JSONObject desc = new JSONObject(weather.getString(0));
        String descripcion = desc.getString("description");
        TiempoActual tiempoActual = new TiempoActual(temp, sensacion, humedad, presion, descripcion);
        return tiempoActual;
    }

    public List<Prevision> getPrevisionDiaria (Ciudad c) throws JSONException {
        List<Prevision> previsiones = new ArrayList<>();
        APIGeocode apiGeocode = new APIGeocode();
        Coordenadas coordenadas = apiGeocode.getCoordenadasDeToponimo(c.getNombre());
        String raw = APIHelper.getBody("https://api.openweathermap.org/data/2.5/onecall?" +
                "lat=" + coordenadas.getLatitud() +"&lon="+ coordenadas.getLongitud() +
                "&exclude=current,minutely,hourly&appid=" + apiKey + "&units=metric" + "&lang=es");
        JSONObject obj = new JSONObject(raw);
        JSONArray daily = obj.getJSONArray("daily");
        int i = 0;
        while (i < 2){
            JSONObject aux = (JSONObject) daily.get(i);
            JSONObject temp = (JSONObject) aux.get("temp");
            String tMax = temp.getString("max");
            String tMin = temp.getString("min");
            String humedad = aux.getString("humidity");
            JSONArray weather = aux.getJSONArray("weather");
            JSONObject desc = new JSONObject(weather.getString(0));
            String descripcion = desc.getString("description");
            Prevision p = new Prevision(tMax, tMin, humedad, descripcion, c);
//            System.out.println(p.toString());;
            previsiones.add(p);
            i++;
        }
        return previsiones;
    }
}
