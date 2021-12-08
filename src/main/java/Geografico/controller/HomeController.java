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

class UserValidator implements Validator {
	@Override
	public boolean supports(Class<?> cls) {
		return UserDetails.class.isAssignableFrom(cls);
	}
	@Override
	public void validate(Object obj, Errors errors) {
		// Exercici: Afegeix codi per comprovar que
		// l'usuari i la contrasenya no estiguen buits
		UserDetails userDetails = (UserDetails) obj;
		if (userDetails.getPassword().trim().equals("") || userDetails.getUsername().trim().equals("")) {
			errors.rejectValue("username", "obligatori", "Cal introduir un valor per a l'usuari i contrasenya");
		}
	}
}

@Controller
public class HomeController {

	private DataBaseFunctions dataBaseFunctions = new DataBaseFunctions(DataBaseConnector.getConnection());

	@RequestMapping(value = "/")
	public String home(Model model){
		return "home";
	}

	//habrá que ponerlo como post
	@RequestMapping(value="/menuPrincipal")
	public String redirigirPaginaPrincipal(Model model){
		return "principal/menuPrincipal";
	}

	@RequestMapping(value="/Ubicaciones")
	public String redirigirUbicacion(Model model){
		model.addAttribute("Coordenadas", new Coordenadas(0,0));
		System.out.println("A \"/Ubicaciones\"");
		return "principal/ubicacion";
	}

	//https://stackoverflow.com/questions/30639818/how-to-do-request-mapping-with-form-action
	@RequestMapping(value="/login")
	public String loginUsuario( @ModelAttribute("user1") UserDetails user) {
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
				return "principal/menuPrincipal";
		}
	}

	@RequestMapping(value="/register")
	public String registrarUsuario( @ModelAttribute("user1") UserDetails user) throws SQLException {
		dataBaseFunctions.addUsuario(new Usuario(user.getUsername(), user.getPassword()));
		return "home";
	}

}
