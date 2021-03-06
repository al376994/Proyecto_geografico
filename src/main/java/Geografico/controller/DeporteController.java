package Geografico.controller;

import Geografico.model.*;
import Geografico.model.API.APISportsData;
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
@RequestMapping(value="/deportes")
public class DeporteController {
    private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    private final APISportsData APISportsData = new APISportsData();

    @RequestMapping(value = "/elegirLiga")
    public String redirigirLiga(Model model, HttpSession session) throws SQLException, FileNotFoundException, JSONException {
        model.addAttribute("ligas", APISportsData.getLigas());
        return "principal/elegirLiga";
    }

    @RequestMapping("/formularioLigaClas/{liga}")
    public String formularioLigaClasificacion(Model model, @PathVariable String liga, HttpSession session) throws JSONException {
        session.setAttribute("liga", liga);
        List<EquipoClasificacion> equipos = APISportsData.getClasificacionUsuario(liga);
        model.addAttribute("equipos", equipos);
        EquipoClasificacion e1 = equipos.get(1);
        return "principal/clasificacion";
    }

    @RequestMapping("/formularioLigaPart/{liga}")
    public String formularioLigaPartidos(Model model, @PathVariable String liga, HttpSession session) throws JSONException {
        session.setAttribute("liga", liga);
        List<Partido> partidos = APISportsData.getPartidosUsuario(liga, false);
        model.addAttribute("partidos",partidos);
        return "principal/partidos";
    }

    @RequestMapping("/formularioLigaPartPas/{liga}")
    public String formularioLigaPartidosPasados(Model model, @PathVariable String liga, HttpSession session) throws JSONException {
        session.setAttribute("liga", liga);
        List<Partido> partidos = APISportsData.getPartidosUsuario(liga, true);
        model.addAttribute("partidos",partidos);
        return "principal/partidos";
    }
}
