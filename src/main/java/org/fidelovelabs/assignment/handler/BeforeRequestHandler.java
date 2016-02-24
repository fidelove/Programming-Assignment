package org.fidelovelabs.assignment.handler;

import spark.Filter;
import spark.Request;
import spark.Response;

public class BeforeRequestHandler implements Filter {

	@Override
	public void handle(Request request, Response response) throws Exception {
		response.header("Access-Control-Allow-Origin", "*");
	}
}