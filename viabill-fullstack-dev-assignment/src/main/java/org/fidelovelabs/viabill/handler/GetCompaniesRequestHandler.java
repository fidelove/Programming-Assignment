package org.fidelovelabs.viabill.handler;

import java.util.Map;

import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;

public class GetCompaniesRequestHandler extends AbstractRequestHandler {

	public GetCompaniesRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {
		return new HandlerResponseBean(200, gson.toJson(mapCompanies.values()));
	}
}