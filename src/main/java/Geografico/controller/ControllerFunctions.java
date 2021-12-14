package Geografico.controller;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class ControllerFunctions {
	static boolean checkIsLogged(Model model, HttpSession session, String nextPage) {
		if(session.getAttribute("user") == null) {
			session.setAttribute("nextPage", nextPage);
			return true;
		}
		else return false;
	}
}
