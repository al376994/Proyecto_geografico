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
import org.springframework.web.bind.annotation.RequestMapping;

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

		if (serviciosActivos.contains(APIHelper.WEATHERAPI))
			model.addAttribute("ubicacionesWeather", usuario.getUbicacionesConServicioAPI(APIHelper.WEATHERAPI));
		if (serviciosActivos.contains(APIHelper.AIRPOLUTIONAPI))
			model.addAttribute("ubicacionesAirPolution", usuario.getUbicacionesConServicioAPI(APIHelper.AIRPOLUTIONAPI));
			model.addAttribute("weather", usuario.getWeather());
		return "principal/servicios";
	}
}
