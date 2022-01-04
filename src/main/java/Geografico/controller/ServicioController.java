package Geografico.controller;

import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIHelper;
import Geografico.model.API.APIOpenWeather;
import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.Ubicacion;
import Geografico.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value="/servicios")
public class ServicioController {

	private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
	private final APIGeocode apiGeocode = new APIGeocode();
	private final APIOpenWeather apiOpenWeather = new APIOpenWeather();

	@RequestMapping(value="")
	public String redirigirServicio(Model model, HttpSession session) throws SQLException {
		if(ControllerFunctions.checkIsLogged(model, session, "/servicios")) return "redirect:/login";
		Usuario usuario = (Usuario)session.getAttribute("user");
		List<String> serviciosActivos = usuario.getServiciosAPI();
		model.addAttribute("serviciosActivos", serviciosActivos);

		model.addAttribute("ubicacionesWeather", usuario.getUbicacionesConServicioAPI(APIHelper.WEATHERAPI));
		model.addAttribute("weather", usuario.getWeather());

		model.addAttribute("ubicacionesAirPolution", usuario.getUbicacionesConServicioAPI(APIHelper.AIRPOLUTIONAPI));
		model.addAttribute("airPolution", usuario.getAirPolution());

		return "principal/servicios";
	}

	@RequestMapping(value="activar/WEATHERAPI")
	public String activarServicioWeather(@SessionAttribute("user") Usuario usuario) {
		usuario.activarServicioAPI(APIHelper.WEATHERAPI);
		return "redirect:/servicios";
	}

	@RequestMapping(value="desactivar/WEATHERAPI")
	public String desactivarServicioWeather(@SessionAttribute("user") Usuario usuario) {
		usuario.desactivarServicioAPI(APIHelper.WEATHERAPI);
		return "redirect:/servicios";
	}

	@RequestMapping(value="activar/AIRPOLUTIONAPI")
	public String activarServicioAirPolution(@SessionAttribute("user") Usuario usuario) {
		usuario.activarServicioAPI(APIHelper.AIRPOLUTIONAPI);
		return "redirect:/servicios";
	}

	@RequestMapping(value="desactivar/AIRPOLUTIONAPI")
	public String desactivarServicioAirPolution(@SessionAttribute("user") Usuario usuario) {
		usuario.desactivarServicioAPI(APIHelper.AIRPOLUTIONAPI);
		return "redirect:/servicios";
	}
}
