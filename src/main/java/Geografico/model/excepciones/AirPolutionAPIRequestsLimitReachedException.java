package Geografico.model.excepciones;

public class AirPolutionAPIRequestsLimitReachedException extends Exception{
	private final String reason;
	public AirPolutionAPIRequestsLimitReachedException(String reason) {
		this.reason = reason;
	}

	@Override
	public String getMessage() {
		return reason;
	}
}
