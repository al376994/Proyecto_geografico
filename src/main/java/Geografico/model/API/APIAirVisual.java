package Geografico.model.API;

import Geografico.model.Polucion;
import Geografico.model.Ciudad;
import Geografico.model.Ubicacion;
import Geografico.model.excepciones.AirPolutionAPIRequestsLimitReachedException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class APIAirVisual implements APIAirVisualInterface{

    private final String key = APIHelper.airVisualKey;

    @Override
    public boolean validarToponimo(String toponimo) {
        return false;
    }

    @Override
    public boolean validarToponimoExaustivo(String ciudad, String provincia, String pais) {
        String raw = APIHelper.getBody("https://api.airvisual.com/v2/city?city=" + ciudad + "&state=" + provincia + "&country=" + pais + "&key=" + key);
		JSONObject jObject;
        try {
			jObject = new JSONObject(raw);
			String status = jObject.get("status").toString();
			if (status.equals("success")) return true;
		} catch (JSONException e) {
			e.printStackTrace();
		}
        return false;
	}

    @Override
    public Ciudad getCiudadCercana() throws JSONException {
        String raw = APIHelper.getBody("http://api.airvisual.com/v2/nearest_city?key="+key);
        JSONObject obj = new JSONObject(raw);
        JSONObject data = obj.getJSONObject("data");
        String nombre = data.getString("city");
        String state = data.getString("state");
        String country = data.getString("country");
        Ciudad c = new Ciudad(nombre,null,country,state);
        return c;
    }

    public Polucion getPolucionCiudadCercana() throws JSONException {
        //InetAddress.getLocalHost()
        String raw = APIHelper.getBody("http://api.airvisual.com/v2/nearest_city?key="+key);
        JSONObject obj = new JSONObject(raw);
        JSONObject data = obj.getJSONObject("data");
        JSONObject current = data.getJSONObject("current");
        JSONObject pollution = current.getJSONObject("pollution");
//        System.out.println(pollution);
        int aqius = pollution.getInt("aqius");
        int aqicn = pollution.getInt("aqicn");
        String maincn = pollution.getString("maincn");
        String mainus = pollution.getString("mainus");
        return new Polucion(aqius,aqicn,mainus,maincn);
    }

    public Polucion getPolucionUbicacion(Ubicacion u) throws JSONException, AirPolutionAPIRequestsLimitReachedException {
        String raw = APIHelper.getBody("http://api.airvisual.com/v2/nearest_city?lat="
                + u.getLatitud() + "&lon=" + u.getLongitud() + "&key="+key);

        JSONObject obj = new JSONObject(raw);
        JSONObject data = obj.getJSONObject("data");
        if (!data.has("current")) {
            if (data.getString("message").equals("call_per_minute_limit_reached"))
                throw new AirPolutionAPIRequestsLimitReachedException(
                        "Se han alcanzado el n??mero m??ximo de solicitudes a la API, recarga la p??gina ahora o " +
                        "dentro de un minuto para solucionar el problema."
                );
            else if (data.getString("message").equals("call_per_day_limit_reached"))
                throw new AirPolutionAPIRequestsLimitReachedException(
                        "Se han alcanzado el n??mero m??ximo de solicitudes a la API diarias, hasta el proximo dia " +
                                "no se podr?? obtener esta informacion."
                );
        }
        JSONObject current = data.getJSONObject("current");
        JSONObject pollution = current.getJSONObject("pollution");
        int aqius = pollution.getInt("aqius");
        int aqicn = pollution.getInt("aqicn");
        String maincn = pollution.getString("maincn");
        String mainus = pollution.getString("mainus");
        return new Polucion(aqius,aqicn,mainus,maincn);
    }

    public String getDescripcion(){
        return "API para obtener la calidad del aire";
    }

    public String getPeticiones(){
        return "10.000 peticiones/mes";
    }

    public List<String> getAtributos(){
        List<String> l = new ArrayList<>();
        l.add("Calidad del aire seg??n USA");
        l.add("Calidad del aire seg??n China");
        return l;
    }


    public String getNombre(){
        return "AirVisual";
    }
}
