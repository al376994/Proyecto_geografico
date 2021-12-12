package Geografico.model.excepciones;

public class NotFoundPlaceException extends Exception{
	public NotFoundPlaceException(String ubicacion) {
		super("No se ha encontrado " + ubicacion);
	}
}
