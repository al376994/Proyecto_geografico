package Geografico.controller;

import Geografico.model.Coordenadas;
import Geografico.model.Ubicacion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UbicacionController {

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=validarCoordenadas")
    public String validarCoordenadas(Model model, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println("Validar Coordenadas: " + ubicacion.getLatitud());
        return "redirect:/Ubicaciones";
    }

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=validarToponimo")
    public String validarToponimo(Model model, @ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println("Validar Toponimo: " + ubicacion.getNombre());
        return "redirect:/Ubicaciones";
    }

    @RequestMapping(value = "/Ubicaciones/añadir", method = RequestMethod.POST, params = "action=añadir")
    public String añadirUbicacion(Model model,@ModelAttribute("Ubicacion") Ubicacion ubicacion){
        System.out.println(ubicacion.getLatitud());
        return "redirect:/Ubicaciones";
    }
}
