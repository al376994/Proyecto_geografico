package Geografico.controller;

import Geografico.model.DataBaseConnector;
import Geografico.model.DataBaseFunctions;
import Geografico.model.UserDetails;
import Geografico.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

	private final DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		return "login";
	}

	@RequestMapping(value="/login", method= RequestMethod.POST)
	public String loginUsuario( @ModelAttribute("user1") UserDetails user, HttpSession session, Model model) throws SQLException {
		// Comprova que el login siga correcte
		int estado = dataBaseFunctions.iniciarSesion(user.getUsername(), user.getPassword());
		switch (estado){
			case 0:
				//no registrado
				return "register";
			case 1:
				//bad password
				return "login";
			default:
				//bien
				session.setAttribute("user", dataBaseFunctions.getUsuario(user.getUsername(), user.getPassword()));
				if (session.getAttribute("nextPage") == null) session.setAttribute("nextPage", "/");
				return "redirect:" + session.getAttribute("nextPage");
		}
	}

	@RequestMapping(value="/register")
	public String registrarUsuario( @ModelAttribute("user1") UserDetails user, HttpSession session) throws SQLException {
		dataBaseFunctions.addUsuario(new Usuario(user.getUsername(), user.getPassword()));
		session.setAttribute("user", dataBaseFunctions.getUsuario(user.getUsername(), user.getPassword()));
		if (session.getAttribute("nextPage") == null) session.setAttribute("nextPage", "/");
		return "redirect:" + session.getAttribute("nextPage");
	}

}
