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
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/capitalesEspana")
    public String redirigirCapitalesEspana(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        List<Ciudad> provinciasEspana = APIOpenDataSoft.getCapitales();
        List<Ciudad> capitales = APIOpenDataSoft.cambiarPorEspacios(provinciasEspana);
        model.addAttribute("capitales", capitales);
        return "principal/elegirTiempo";
    }

    @RequestMapping(value = "/capitalesCV")
    public String redirigirCapitalesCV(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        List<Ciudad> provinciasEspana = APIOpenDataSoft.getCapitalesCV();
        List<Ciudad> capitales = APIOpenDataSoft.cambiarPorEspacios(provinciasEspana);
        model.addAttribute("capitales", capitales);
        return "principal/elegirTiempo";
    }

    @RequestMapping(value = "/elegirTiempo")
    public String redirigirTiempo(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        List<Ciudad> provinciasEspana = APIOpenDataSoft.getCapitales();
        List<Ciudad> capitales = APIOpenDataSoft.cambiarPorEspacios(provinciasEspana);
        model.addAttribute("capitales", capitales);
        return "principal/elegirTiempo";
    }

    @RequestMapping("/formularioTiempoProv/{capital}")
    public String formularioformularioTiempoProv(Model model, @PathVariable String capital, HttpSession session) throws JSONException {
        String[] capitalCon = capital.split(" ");
        Ciudad c = new Ciudad(capitalCon[0],capitalCon[1],capitalCon[2],capitalCon[3]);
        List<Prevision> p = APIOpenWeather.getPrevisionDiaria(c);
        PrevisionCiudad aux = new PrevisionCiudad(c,p);
        p = aux.getPrevisionList();
        model.addAttribute("capital", c);
        model.addAttribute("previsiones", p);
        return "principal/tiempoCapital";
    }

}
