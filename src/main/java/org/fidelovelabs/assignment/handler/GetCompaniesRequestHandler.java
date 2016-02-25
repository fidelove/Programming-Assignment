package org.fidelovelabs.assignment.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.fidelovelabs.assignment.model.BasicCompanyBean;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetCompaniesRequestHandler extends AbstractRequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetCompaniesRequestHandler.class.getName());

	public GetCompaniesRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		logInfo(LOGGER, "Get Companies request");

		List<BasicCompanyBean> resultCompanies = new ArrayList<BasicCompanyBean>();

		mapCompanies.values().forEach(
				(company) -> resultCompanies.add(new BasicCompanyBean(company.getIdCompany(), company.getName())));

		Collections.sort(resultCompanies);

		logInfo(LOGGER, "Get Companies request finished");
		return new HandlerResponseBean(200, gson.toJson(resultCompanies));
	}
}