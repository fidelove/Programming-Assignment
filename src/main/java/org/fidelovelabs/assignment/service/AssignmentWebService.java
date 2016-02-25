package org.fidelovelabs.assignment.service;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

import java.util.Map;

import org.fidelovelabs.assignment.handler.AddOwnerRequestHandler;
import org.fidelovelabs.assignment.handler.BeforeRequestHandler;
import org.fidelovelabs.assignment.handler.CreateCompanyRequestHandler;
import org.fidelovelabs.assignment.handler.GetCompaniesRequestHandler;
import org.fidelovelabs.assignment.handler.GetCompanyDetailsRequestHandler;
import org.fidelovelabs.assignment.handler.OptionsRequestHandler;
import org.fidelovelabs.assignment.handler.UpdateCompanyDetailsRequestHandler;
import org.fidelovelabs.assignment.model.CompanyBean;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;

public class AssignmentWebService {

	public static void main(String[] args) {
		Config config = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);
		Map<Long, CompanyBean> mapCompanies = instance.getMap("companies");
		IAtomicLong idCompany = instance.getAtomicLong("idCompany");

		port(9090);

		// form web
		staticFileLocation("/website");

		post("/createCompany", new CreateCompanyRequestHandler(mapCompanies, idCompany));
		get("/getCompanies", new GetCompaniesRequestHandler(mapCompanies));
		get("/getCompany/:idcompany", new GetCompanyDetailsRequestHandler(mapCompanies));
		put("/updateCompany/:idcompany", new UpdateCompanyDetailsRequestHandler(mapCompanies));
		put("/addOwner/:idcompany", new AddOwnerRequestHandler(mapCompanies));

		// CORS habilitation
		options("/*", new OptionsRequestHandler());
		before(new BeforeRequestHandler());

	}
}