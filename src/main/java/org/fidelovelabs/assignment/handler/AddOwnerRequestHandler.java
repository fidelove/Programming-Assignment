package org.fidelovelabs.assignment.handler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.reflect.TypeToken;

public class AddOwnerRequestHandler extends AbstractRequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(AddOwnerRequestHandler.class.getName());

	public AddOwnerRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		logInfo(LOGGER, "Add Owner request");

		HandlerResponseBean response = null;

		CompanyBean companyBean = getCompanyById(map);

		if (companyBean != null) {

			List<String> beneficiaOwner = new ArrayList<String>(companyBean.getBeneficiaOwner());

			Type listType = new TypeToken<ArrayList<String>>() {
			}.getType();
			List<String> newBeneficiaOwners = fromJson(body, listType);

			if (CollectionUtils.isNotEmpty(newBeneficiaOwners)) {

				for (String newBeneficiaOwner : newBeneficiaOwners) {
					if (!beneficiaOwner.contains(newBeneficiaOwner)) {
						beneficiaOwner.add(newBeneficiaOwner);
					} else {
						logError(LOGGER, String.format("Wrong Request: Company already contains beneficial owner %s",
								newBeneficiaOwner));
						response = new HandlerResponseBean(400, String.format(
								"{ \"error\" : \"Company already contains beneficial owner %s\"}", newBeneficiaOwner));
						break;
					}
				}

				if (response == null) {
					logInfo(LOGGER, "New AddOwner succesfully added");
					companyBean.setBeneficiaOwner(beneficiaOwner);
					mapCompanies.replace(companyBean.getIdCompany(), companyBean);

					response = new HandlerResponseBean(200, gson.toJson(companyBean, CompanyBean.class));
				}
			} else {
				logError(LOGGER, "Wrong Request: No valid beneficial owners");
				response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: No valid beneficial owners\"}");
			}

		} else {
			logError(LOGGER, "Wrong Request: Company does not exist");
			response = new HandlerResponseBean(400,
					"{ \"error\" : \"Company does not exist. Please send the correct idCompany\"}");
		}

		logInfo(LOGGER, "Add Owner request finished");
		return response;
	}
}