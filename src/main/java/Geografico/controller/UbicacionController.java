package Geografico.controller;

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

    @RequestMapping(value="/lista")
    public String redirigirUbicacion(Model model, HttpSession session) throws SQLException {
        if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/lista")) return "redirect:/login";
        model.addAttribute("Ubicacion", new Ubicacion(0,0, ""));
        List<Ubicacion> ubicaciones = ((Usuario)session.getAttribute("user")).getUbicaciones();
        model.addAttribute("ubicaciones", ubicaciones);
        return "principal/ubicaciones";
    }

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=validarCoordenadas")
    public String validarCoordenadas(Model model, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println("Validar Coordenadas: " + ubicacion.getLatitud());
        return "redirect:/Ubicaciones";
    }

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=validarToponimo")
    public String validarToponimo(Model model, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println("Validar Toponimo: " + ubicacion.getNombre());
        return "redirect:/Ubicaciones";
    }

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=añadir")
    public String añadirUbicacion(Model model,@ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println(ubicacion.getLatitud());
        return "redirect:/Ubicaciones";
    }
}
