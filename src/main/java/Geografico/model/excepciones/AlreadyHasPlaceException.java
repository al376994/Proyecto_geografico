package Geografico.model.excepciones;

public class AlreadyHasPlaceException extends Exception{

	public AlreadyHasPlaceException(String ubicacion) {
		super("Ya tienes dada de alta la ubicacion: " + ubicacion);
	}
}
