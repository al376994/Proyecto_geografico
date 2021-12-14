package Geografico.controller;

import Geografico.model.API.APIGeocode;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value="/ubicaciones")
public class UbicacionController {

    private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
    private final APIGeocode apiGeocode = new APIGeocode();

    @RequestMapping(value="/lista")
    public String redirigirUbicacion(Model model, HttpSession session) throws SQLException {
        if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/lista")) return "redirect:/login";
        if (session.getAttribute("lastUbicacion") == null)
            session.setAttribute("lastUbicacion", new Ubicacion(0,0, ""));
        model.addAttribute("Ubicacion", session.getAttribute("lastUbicacion"));
        List<Ubicacion> ubicaciones = ((Usuario)session.getAttribute("user")).getUbicaciones();
        model.addAttribute("ubicaciones", ubicaciones);
        model.addAttribute("coordenadasValidas", session.getAttribute("coordenadasValidas"));
        return "principal/ubicaciones";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=validarCoordenadas")
    public String validarCoordenadas(Model model, HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        Usuario usuario = (Usuario) session.getAttribute("user");
        boolean coordenadasValidas = apiGeocode.validarCoordenadas(ubicacion.getLatitud(), ubicacion.getLongitud());
        System.out.println("Validar Coordenadas: " + coordenadasValidas);
        session.setAttribute("coordenadasValidas", coordenadasValidas);
        session.setAttribute("lastUbicacion", ubicacion);
        return "redirect:/ubicaciones/lista";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=validarToponimo")
    public String validarToponimo(Model model, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println("Validar Toponimo: " + apiGeocode.validarToponimo(ubicacion.getNombre()));
        return "";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=añadir")
    public String addUbicacion(Model model,@ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println(ubicacion.getNombre());
        return "";
    }
}
