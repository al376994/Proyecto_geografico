package Geografico.model.API;

import org.json.JSONException;
import org.json.JSONObject;

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


}
