package Geografico.model.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

class APIHelper {
	// Listado do keys para las APIs
	static final String airVisualKey = "bf324fc3-3c44-4f50-bd19-836179b019b8";
	static final String geoCodeKey = "136807723723026938676x50228";
	
	private static final HttpClient client = HttpClient.newHttpClient();

	// Esta funcion crea los recursos necesarios para hacer una peticion GET a una URL
	// y devuelve el contenido en una String en json
	// De normal solo hay que usar esta funcion desde fuera
	static String getBody(String givenURI) {
		HttpRequest request = makeRequest(givenURI);
		if (request == null) return null;
		HttpResponse<String> response = getResponse(request);
		return response.body();
	}
/*
	// Esta funcion hace lo mismo que la anterior pero hace una peticion PUT, algunas
	// APIs lo hace asi, de momento se han encontrado formas de usar solo GET ya que
	// esta funcion devuelve xml en vez de json por algun motivo
	public static String getBody(String givenURI, String body) {
		HttpRequest request = makePost(givenURI, body);
		if (request == null) return null;
		HttpResponse<String> response = getResponse(request);
		return response.body();
	}
*/
	// Esta funcion crea una peticion GET a partir de una URL
	static private HttpRequest makeRequest(String givenURI) {
		return HttpRequest.newBuilder(URI.create(givenURI))
				.header("accept", "application/json")
				.build();
	}

	// Esta funcion crea una peticion PUT a partir de una URL, es posible que el
	// problema de que devuelva sml en vez de json resida en esta funcion
	// nadie la usa de momento
	static private HttpRequest makePost(String givenURI, String body) {
		return HttpRequest.newBuilder(URI.create(givenURI))
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
	}

	// Esta funcion hace la peticion y recive la respesta
	static private HttpResponse<String> getResponse(HttpRequest request) {
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return response;
	}
}