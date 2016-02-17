package org.fidelovelabs.viabill.handler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;

import com.google.gson.reflect.TypeToken;

public class AddOwnerRequestHandler extends AbstractRequestHandler {

	public AddOwnerRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		HandlerResponseBean response = null;

		CompanyBean companyBean = getCompanyById(map);

		if (companyBean != null) {

			List<String> beneficiaOwner = new ArrayList<String>(companyBean.getBeneficiaOwner());

			Type listType = new TypeToken<ArrayList<String>>() {
			}.getType();
			List<String> newBeneficiaOwners = gson.fromJson(body, listType);

			for (String newBeneficiaOwner : newBeneficiaOwners) {
				if (!beneficiaOwner.contains(newBeneficiaOwner)) {
					beneficiaOwner.add(newBeneficiaOwner);
				} else {
					response = new HandlerResponseBean(400, String
							.format("{ error : \"Company already contains beneficial owner %s\"}", newBeneficiaOwner));
					break;
				}
			}

			if (response == null) {
				companyBean.setBeneficiaOwner(beneficiaOwner);
				mapCompanies.replace(companyBean.getIdCompany(), companyBean);

				response = new HandlerResponseBean(200, gson.toJson(companyBean, CompanyBean.class));
			}

		} else {
			response = new HandlerResponseBean(400,
					"{ error : \"Company does not exist. Please send the correct idCompany\"}");
		}

		return response;
	}
}