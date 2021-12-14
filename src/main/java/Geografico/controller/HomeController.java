package Geografico.controller;

import Geografico.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

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

	//habrá que ponerlo como post
	@RequestMapping(value="/menuPrincipal")
	public String redirigirPaginaPrincipal(Model model, HttpSession session){
		if (ControllerFunctions.checkIsLogged(model, session, "/menuPrincipal")) return "redirect:/login";
		return "principal/menuPrincipal";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:/";
	}
}
