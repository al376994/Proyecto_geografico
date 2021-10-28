package Geografico.controller.info;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactUsController {

	@RequestMapping(value="/contact_us")
	public String contact(Model model) {
		return "/info/contact_us";
	}

}
