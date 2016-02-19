package org.fidelovelabs.viabill.handler;

import spark.Request;
import spark.Response;
import spark.Route;

public class OptionsRequestHandler implements Route {

	@Override
	public Object handle(Request request, Response response) throws Exception {

		String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
		if (accessControlRequestHeaders != null) {
			response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
		}

		String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
		if (accessControlRequestMethod != null) {
			response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
		}

		return "OK";
	}
}