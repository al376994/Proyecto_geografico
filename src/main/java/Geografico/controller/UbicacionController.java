package Geografico.controller;

import Geografico.model.*;
import Geografico.model.API.APIGeocode;
import Geografico.model.API.APIHelper;
import Geografico.model.excepciones.AlreadyHasPlaceException;
import Geografico.model.excepciones.CoordenadasExcepcion;
import Geografico.model.excepciones.NotFoundPlaceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/ubicaciones")
public class UbicacionController {

	private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());
	private final APIGeocode apiGeocode = new APIGeocode();

	@RequestMapping(value="/lista")
	public String redirigirUbicacion(Model model, HttpSession session) throws SQLException {
		if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/lista")) return "redirect:/login";
		if (session.getAttribute("lastUbicacion") == null){
			session.setAttribute("lastUbicacion", new Ubicacion(0,0, ""));
			session.removeAttribute("coordenadasValidas");
			session.removeAttribute("toponimoValido");
		}

		Usuario usuario = (Usuario)session.getAttribute("user");

		model.addAttribute("Ubicacion", session.getAttribute("lastUbicacion"));
		List<Ubicacion> ubicaciones = usuario.getUbicacionesActivas();
		model.addAttribute("ubicaciones", ubicaciones);
		model.addAttribute("coordenadasValidas", session.getAttribute("coordenadasValidas"));
		model.addAttribute("toponimoValido", session.getAttribute("toponimoValido"));

		model.addAttribute("weather", usuario.getWeather());
		model.addAttribute("airPolution", usuario.getAirPolution());
		return "principal/ubicaciones";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "action=validarCoordenadas")
	public String validarCoordenadas(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
		ubicacion.setNombre( ((Ubicacion)session.getAttribute("lastUbicacion")).getNombre() );
		boolean coordenadasValidas = apiGeocode.validarCoordenadas(ubicacion.getLatitud(), ubicacion.getLongitud());
		System.out.println("Validar Coordenadas: " + coordenadasValidas);
		session.setAttribute("coordenadasValidas", coordenadasValidas);
		session.setAttribute("lastUbicacion", ubicacion);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "action=validarToponimo")
	public String validarToponimo(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
		ubicacion.setLatitud( ((Ubicacion)session.getAttribute("lastUbicacion")).getLatitud() );
		ubicacion.setLongitud( ((Ubicacion)session.getAttribute("lastUbicacion")).getLongitud() );
		boolean toponimoValido = apiGeocode.validarToponimo(ubicacion.getNombre());
		System.out.println("Validar Toponimo: " + toponimoValido);
		session.setAttribute("toponimoValido", toponimoValido);
		session.setAttribute("lastUbicacion", ubicacion);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "action=añadirPorCoordenadas")
	public String addUbicacionPorCoordenadas(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion, RedirectAttributes redirectAttributes) {
		Usuario usuario = (Usuario) session.getAttribute("user");
		try {
			usuario.altaUbicacionCoordenadas(ubicacion.getLatitud(), ubicacion.getLongitud());
		} catch (CoordenadasExcepcion e) {
			session.setAttribute("coordenadasValidas", false);
			session.setAttribute("lastUbicacion", ubicacion);
		} catch (AlreadyHasPlaceException e) {
			e.printStackTrace();
		}
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "action=añadirPorToponimo")
	public String addUbicacionPorToponimo(HttpSession session, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
		Usuario usuario = (Usuario) session.getAttribute("user");
		try {
			usuario.altaUbicacionToponimo(ubicacion.getNombre());
		} catch (AlreadyHasPlaceException e) {
			e.printStackTrace();
		}
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/ubicacion/{toponimo}")
	public String muestraDestallesUbicacion(Model model, HttpSession session, @PathVariable String toponimo) {
		if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/ubicacion/" + toponimo)) return "redirect:/login";
		Usuario usuario = (Usuario)session.getAttribute("user");
		Ubicacion ubicacion = usuario.getUbicacion(toponimo);
		model.addAttribute("ubicacion", ubicacion);
		model.addAttribute("weather", usuario.getWeather());
		model.addAttribute("airPolution", usuario.getAirPolution());
		return "principal/ubicacion";
	}

	@RequestMapping(value = "/lista/desactivadas")
	public String listaUbicacionesDesactivadas(Model model, HttpSession session) throws SQLException {
		if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/lista/desactivadas")) return "redirect:/login";

		Usuario usuario = (Usuario)session.getAttribute("user");

		List<Ubicacion> ubicaciones = usuario.getUbicacionesDesactivadas();
		model.addAttribute("ubicaciones", ubicaciones);
		model.addAttribute("weather", usuario.getWeather());
		return "principal/ubicacionesDesactivadas";
	}

	@RequestMapping(value = "/lista/historial")
	public String historialUbicaciones(Model model, HttpSession session) throws SQLException {
		if(ControllerFunctions.checkIsLogged(model, session, "/ubicaciones/lista/historial")) return "redirect:/login";

		Usuario usuario = (Usuario)session.getAttribute("user");

		List<Ubicacion> historial = usuario.getHistorialUbicaciones();
		model.addAttribute("historial", historial);
		return "principal/historial";
	}

	@RequestMapping(value = "reactivar/{toponimo}")
	public String reactivarUbicacionHistorial(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) {
		usuario.reactivarUbicacionDelHistorial(toponimo);
		return "redirect:/ubicaciones/lista/historial";
	}

	@RequestMapping(value = "activar/{toponimo}")
	public String activarUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) {
		usuario.activarUbicacion(usuario.getUbicacion(toponimo));
		return "redirect:/ubicaciones/lista/desactivadas";
	}

	@RequestMapping(value = "desactivar/{toponimo}")
	public String desactivarUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) {
		usuario.desactivarUbicacion(usuario.getUbicacion(toponimo));
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "activar/weather/{toponimo}")
	public String activarWeatherUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) throws NotFoundPlaceException {
		usuario.altaServicioUbicacion(usuario.getUbicacion(toponimo), APIHelper.WEATHERAPI);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "desactivar/weather/{toponimo}")
	public String desactivarWeatherUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) {
		usuario.bajaServicioUbicacion(usuario.getUbicacion(toponimo), APIHelper.WEATHERAPI);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "activar/airPolution/{toponimo}")
	public String activarAirPolutionUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) throws NotFoundPlaceException {
		usuario.altaServicioUbicacion(usuario.getUbicacion(toponimo), APIHelper.AIRPOLUTIONAPI);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "desactivar/airPolution/{toponimo}")
	public String desactivarAirPolutionUbicacion(@PathVariable String toponimo, @SessionAttribute("user") Usuario usuario) {
		usuario.bajaServicioUbicacion(usuario.getUbicacion(toponimo), APIHelper.AIRPOLUTIONAPI);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/toggleFavorito/{toponimo}")
	public String toggleFavoritoUbicacion(@SessionAttribute("user") Usuario usuario, @PathVariable String toponimo) throws SQLException {
		Ubicacion ubicacion = usuario.getUbicacion(toponimo);
		usuario.toggleFavoritoUbicacion(ubicacion);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/eliminar/{toponimo}")
	public String deleteUbicacion(@SessionAttribute("user") Usuario usuario, @PathVariable String toponimo) {
		Ubicacion ubicacion = usuario.getUbicacion(toponimo);
		usuario.darDeBajaUbicacion(ubicacion);
		return "redirect:/ubicaciones/lista";
	}

	@RequestMapping(value = "/cambiarAlias")
	public String cambiarAliasUbicacion(@SessionAttribute("user") Usuario usuario, @ModelAttribute("ubicacion") Ubicacion ubicacion) {
		usuario.asignarAliasUbicacion(ubicacion.getNombre(), ubicacion.getAlias());
		return "redirect:ubicaciones/ubicacion/" + ubicacion.getNombre();
	}
}
