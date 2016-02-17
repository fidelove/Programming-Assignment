package org.fidelovelabs.viabill.handler;

import java.util.Map;

import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;

public class GetCompanyDetailsRequestHandler extends AbstractRequestHandler {

	public GetCompanyDetailsRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		HandlerResponseBean response;

		CompanyBean companyBean = getCompanyById(map);

		if (companyBean != null) {
			response = new HandlerResponseBean(200, gson.toJson(companyBean, CompanyBean.class));
		} else {
			response = new HandlerResponseBean(400,
					"{ error : \"Company does not exist. Please send the correct idCompany\"}");
		}

		return response;
	}
}
