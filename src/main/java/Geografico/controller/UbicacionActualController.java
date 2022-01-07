package Geografico.controller;

import Geografico.model.*;
import Geografico.model.API.APIAirVisual;
import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIOpenDataSoft;
import Geografico.model.API.APIOpenWeather;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
@RequestMapping(value="/ubicacionActual")
public class UbicacionActualController {
    private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    private final APIAirVisual apiAirVisual = new APIAirVisual();
    private final APIOpenWeather apiOpenWeather = new APIOpenWeather();
    private final APIOpenDataSoft apiOpenDataSoft = new APIOpenDataSoft();

    @RequestMapping(value="/servicios")
    public String redirigirServicio(Model model, HttpSession session) throws JSONException {
        if(ControllerFunctions.checkIsLogged(model, session, "/ubicacionActual/servicios")) return "redirect:/login";
        Ciudad ciudad = apiAirVisual.getCiudadCercana();
        Ciudad aux = apiOpenDataSoft.cambiarParaOpenWeather(ciudad);
        Polucion polucion = apiAirVisual.getPolucionCiudadCercana();
        TiempoActual tiempoActual = apiOpenWeather.getTiempoActual(aux);
        model.addAttribute("ciudad",ciudad);
        model.addAttribute("polucion",polucion);
        model.addAttribute("tiempo",tiempoActual);
        return "principal/ubicacionActual";
    }
}
