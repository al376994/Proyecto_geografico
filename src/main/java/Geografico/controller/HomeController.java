package Geografico.controller;

import Geografico.model.Coordenadas;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

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

}
