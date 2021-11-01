package Geografico.model.API;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIHelper {
	public static final String airVisualKey = "bf324fc3-3c44-4f50-bd19-836179b019b8";
	public static final String geoCodeKey = "";	//No hace falta
	private static final HttpClient client = HttpClient.newHttpClient();

	public static String getBody(String givenURI) {
		HttpRequest request = makeRequest(givenURI);
		if (request == null) return null;
		HttpResponse<String> response = getResponse(request);
		return response.body();
	}
/*
	public static String getBody(String givenURI, String body) {
		HttpRequest request = makePost(givenURI, body);
		if (request == null) return null;
		HttpResponse<String> response = getResponse(request);
		return response.body();
	}
*/
	static private HttpRequest makeRequest(String givenURI) {
		return HttpRequest.newBuilder(URI.create(givenURI))
				.header("accept", "application/json")
				.build();
	}

	static private HttpRequest makePost(String givenURI, String body) {
		return HttpRequest.newBuilder(URI.create(givenURI))
				.header("Accept", "application/json")
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(body))
				.build();
	}

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