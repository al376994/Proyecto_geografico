package Geografico.controller;

import Geografico.model.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
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
