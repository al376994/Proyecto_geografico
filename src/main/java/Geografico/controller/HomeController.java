package Geografico.controller;

import Geografico.model.*;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
public class HomeController {

	private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	@RequestMapping(value = "/")
	public String home(Model model, HttpSession session){
		Usuario usuario = (Usuario) session.getAttribute("user");
		if (usuario == null) {
			return "redirect:/login";
		}
		else return "redirect:/menuPrincipal";
	}

	@RequestMapping(value="/menuPrincipal")
	public String redirigirPaginaPrincipal(Model model, HttpSession session) {
		if (ControllerFunctions.checkIsLogged(model, session, "/menuPrincipal")) return "redirect:/login";

		Usuario usuario = (Usuario)session.getAttribute("user");
		List<Ubicacion> ubicaciones = usuario.getUbicacionesFavoritas();
		model.addAttribute("ubicaciones", ubicaciones);
		model.addAttribute("weather", usuario.getWeather());
		model.addAttribute("airPolution", usuario.getAirPolution());

		return "principal/menuPrincipal";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}

//	@RequestMapping(value = "/configurar")
//	public String configurar(HttpSession session, Model model){
//		Usuario user = (Usuario) session.getAttribute("user");
//		model.addAttribute("user",user);
//		return "principal/configurar";
//	}
//
//	@RequestMapping(value = "/configurar/formulario/{password}")
//	public String configurarPwd(HttpSession session, @PathVariable String password, Model model){
//		System.out.println(password);
//		return "principal/menuPrincipal";
//	}
//	@RequestMapping(value="/configurar/{password}", method = RequestMethod.GET)
//	public String editarUsuario(Model model, @PathVariable String password, HttpSession session) {
//		Usuario user = (Usuario) session.getAttribute("user");
//		session.setAttribute("oldPwd",);
//		model.addAttribute("user",user);
//		return "principal/configurar";
//	}
//
//	@RequestMapping(value="/update", method = RequestMethod.POST)
//	public String processUpdateSubmit(
//			@ModelAttribute("user") User user,
//			BindingResult bindingResult) {
//		if (bindingResult.hasErrors())
//			return "principal/configurar";
//		dataBaseFunctions.actualizarContraseña(user.getUsername())
//		return "principal/menuPrincipal";
//	}
}
