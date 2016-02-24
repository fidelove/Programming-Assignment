package org.fidelovelabs.assignment.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.fidelovelabs.assignment.model.BasicCompanyBean;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;

public class GetCompaniesRequestHandler extends AbstractRequestHandler {

	public GetCompaniesRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		List<BasicCompanyBean> resultCompanies = new ArrayList<BasicCompanyBean>();

		mapCompanies.values().forEach(
				(company) -> resultCompanies.add(new BasicCompanyBean(company.getIdCompany(), company.getName())));

		Collections.sort(resultCompanies);

		return new HandlerResponseBean(200, gson.toJson(resultCompanies));
	}
}