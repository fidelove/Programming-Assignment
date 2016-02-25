package org.fidelovelabs.assignment.handler;

import java.util.Map;

import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetCompanyDetailsRequestHandler extends AbstractRequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GetCompanyDetailsRequestHandler.class.getName());

	public GetCompanyDetailsRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		logInfo(LOGGER, "Get Company Details request");

		HandlerResponseBean response;

		CompanyBean companyBean = getCompanyById(map);

		if (companyBean != null) {
			logInfo(LOGGER, "Get Company Details request succeed");
			response = new HandlerResponseBean(200, gson.toJson(companyBean, CompanyBean.class));
		} else {
			logError(LOGGER, "Wrong Request: Company does not exist");
			response = new HandlerResponseBean(400,
					"{ \"error\" : \"Company does not exist. Please send the correct idCompany\"}");
		}

		logInfo(LOGGER, "Get Company Details request finished");
		return response;
	}
}
