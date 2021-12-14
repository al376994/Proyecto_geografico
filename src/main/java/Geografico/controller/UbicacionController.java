package Geografico.controller;

import Geografico.model.API.APIGeocode;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import Geografico.model.excepciones.CoordenadasExcepcion;
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
        model.addAttribute("toponimoValido", session.getAttribute("toponimoValido"));
        return "principal/ubicaciones";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=validarCoordenadas")
    public String validarCoordenadas(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        ubicacion.setNombre( ((Ubicacion)session.getAttribute("lastUbicacion")).getNombre() );
        boolean coordenadasValidas = apiGeocode.validarCoordenadas(ubicacion.getLatitud(), ubicacion.getLongitud());
        System.out.println("Validar Coordenadas: " + coordenadasValidas);
        session.setAttribute("coordenadasValidas", coordenadasValidas);
        session.setAttribute("lastUbicacion", ubicacion);
        return "redirect:/ubicaciones/lista";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=validarToponimo")
    public String validarToponimo(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        ubicacion.setLatitud( ((Ubicacion)session.getAttribute("lastUbicacion")).getLatitud() );
        ubicacion.setLongitud( ((Ubicacion)session.getAttribute("lastUbicacion")).getLongitud() );
        boolean toponimoValido = apiGeocode.validarToponimo(ubicacion.getNombre());
        System.out.println("Validar Toponimo: " + toponimoValido);
        session.setAttribute("toponimoValido", toponimoValido);
        session.setAttribute("lastUbicacion", ubicacion);
        return "redirect:/ubicaciones/lista";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=añadirPorCoordenadas")
    public String addUbicacionPorCoordenadas(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion) throws CoordenadasExcepcion {
        Usuario usuario = (Usuario) session.getAttribute("user");
        usuario.altaUbicacionCoordenadas(ubicacion.getLatitud(), ubicacion.getLongitud());
        return "redirect:/ubicaciones/lista";
    }

    @RequestMapping(value = "/añadir", method = RequestMethod.POST, params = "action=añadirPorToponimo")
    public String addUbicacionPorToponimo(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        Usuario usuario = (Usuario) session.getAttribute("user");
        usuario.altaUbicacionToponimo(ubicacion.getNombre());
        return "redirect:/ubicaciones/lista";
    }
}
