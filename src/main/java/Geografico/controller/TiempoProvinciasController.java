package Geografico.controller;

import Geografico.model.API.APIOpenDataSoft;
import Geografico.model.API.APIOpenDataSoftInterface;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.Ciudad;
import Geografico.model.Prevision;
import Geografico.model.PrevisionCiudad;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/tiempo")
public class TiempoProvinciasController {
    private APIOpenDataSoft APIOpenDataSoft = new APIOpenDataSoft();
    private APIOpenWeather APIOpenWeather = new APIOpenWeather();

    @RequestMapping(value = "/CV")
    public String redirigirUbicacion(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        List<Ciudad> provinciasEspana = APIOpenDataSoft.getCapitalesCV();
        List<PrevisionCiudad> previsiones = new ArrayList<>();
        for (Ciudad c : provinciasEspana) {
            List<Prevision> p = APIOpenWeather.getPrevisionDiaria(c);
            PrevisionCiudad previsionCiudad = new PrevisionCiudad(c, p);
            previsiones.add(previsionCiudad);
        }
        model.addAttribute("previsionesEspana", previsiones);
        return "principal/tiempoCV";
    }

    @RequestMapping(value = "/elegirTiempo")
    public String redirigirTiempo(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        return "principal/elegirTiempo";
    }

}
