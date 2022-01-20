package Geografico.controller;

import Geografico.model.API.*;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/servicios")
public class ServicioController {

	private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
	private final APIGeocode apiGeocode = new APIGeocode();
	private final APIOpenWeather apiOpenWeather = new APIOpenWeather();
	private final APIAirVisual apiAirVisual = new APIAirVisual();
	private final APISportsData apiSportsData = new APISportsData();

	@RequestMapping(value="")
	public String redirigirServicio(Model model, HttpSession session) throws SQLException {
		if(ControllerFunctions.checkIsLogged(model, session, "/servicios")) return "redirect:/login";
		Usuario usuario = (Usuario)session.getAttribute("user");
		List<String> serviciosActivos = usuario.getServiciosAPI();
		model.addAttribute("serviciosActivos", serviciosActivos);

		model.addAttribute("ubicacionesWeather", usuario.getUbicacionesConServicioAPI(APIHelper.WEATHERAPI));
		model.addAttribute("weather", usuario.getWeather());
		model.addAttribute("weatherAuto", usuario.getAdicionAutomaticaAPI(APIHelper.WEATHERAPI));

		model.addAttribute("ubicacionesAirPolution", usuario.getUbicacionesConServicioAPI(APIHelper.AIRPOLUTIONAPI));
		model.addAttribute("airPolution", usuario.getAirPolution());
		model.addAttribute("airPolutionAuto", usuario.getAdicionAutomaticaAPI(APIHelper.AIRPOLUTIONAPI));

		//descripción de los servicios
		List<Object> serv = new ArrayList<>();
		serv.add(apiAirVisual);serv.add(apiOpenWeather);serv.add(apiSportsData);serv.add(apiGeocode);
		model.addAttribute("serv", serv);

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

	@RequestMapping(value="activarAdicionAutomatica/WEATHERAPI")
	public String activarAdicionAutomaticaWeather(@SessionAttribute("user") Usuario usuario) {
		usuario.activarAdicionAutomaticaAPI(APIHelper.WEATHERAPI);
		return "redirect:/servicios";
	}

	@RequestMapping(value="desactivarAdicionAutomatica/WEATHERAPI")
	public String desactivarAdicionAutomaticaWeather(@SessionAttribute("user") Usuario usuario) {
		usuario.desactivarAdicionAutomaticaAPI(APIHelper.WEATHERAPI);
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

	@RequestMapping(value="activarAdicionAutomatica/AIRPOLUTIONAPI")
	public String activarAdicionAutomaticaAirPolution(@SessionAttribute("user") Usuario usuario) {
		usuario.activarAdicionAutomaticaAPI(APIHelper.AIRPOLUTIONAPI);
		return "redirect:/servicios";
	}

	@RequestMapping(value="desactivarAdicionAutomatica/AIRPOLUTIONAPI")
	public String desactivarAdicionAutomaticaAirPolution(@SessionAttribute("user") Usuario usuario) {
		usuario.desactivarAdicionAutomaticaAPI(APIHelper.AIRPOLUTIONAPI);
		return "redirect:/servicios";
	}
}
