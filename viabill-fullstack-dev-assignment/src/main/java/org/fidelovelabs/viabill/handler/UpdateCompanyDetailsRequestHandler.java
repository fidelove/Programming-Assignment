package org.fidelovelabs.viabill.handler;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.collections.CollectionUtils;
import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;

public class UpdateCompanyDetailsRequestHandler extends AbstractRequestHandler {

	public UpdateCompanyDetailsRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		HandlerResponseBean response;

		try {

			CompanyBean companyBean = getCompanyById(map);

			if (companyBean != null) {

				CompanyBean updateRequest = fromJson(body, CompanyBean.class);

				if (updateRequest == null) {
					response = new HandlerResponseBean(400,
							"{ error : \"Wrong Request: Request is not a valid JSON\"}");

				} else if (CollectionUtils.isNotEmpty(updateRequest.getBeneficiaOwner())) {
					response = new HandlerResponseBean(400,
							"{ error : \"Wrong Request: In order to update beneficial owners use addOwner\"}");

				} else if (!isEmailValid(updateRequest.getEmail())) {
					response = new HandlerResponseBean(400, "{ error : \"Wrong Request: Invalid Email Address\"}");

				} else if (!isPhoneValid(updateRequest.getPhoneNumber())) {
					response = new HandlerResponseBean(400, "{ error : \"Wrong Request: Invalid phone number\"}");

				} else {

					updateRequest.setIdCompany(companyBean.getIdCompany());
					updateRequest
							.setName(updateRequest.getName() != null ? updateRequest.getName() : companyBean.getName());
					updateRequest.setAddress(
							updateRequest.getAddress() != null ? updateRequest.getAddress() : companyBean.getAddress());
					updateRequest
							.setCity(updateRequest.getCity() != null ? updateRequest.getCity() : companyBean.getCity());
					updateRequest.setCountry(
							updateRequest.getCountry() != null ? updateRequest.getCountry() : companyBean.getCountry());
					updateRequest.setEmail(
							updateRequest.getEmail() != null ? updateRequest.getEmail() : companyBean.getEmail());
					updateRequest.setPhoneNumber(
							updateRequest.getPhoneNumber() != null ? updateRequest.getEmail() : companyBean.getEmail());
					updateRequest.setBeneficiaOwner(companyBean.getBeneficiaOwner());

					Set<ConstraintViolation<CompanyBean>> validate = validator.validate(updateRequest);

					if (validate.isEmpty()) {
						mapCompanies.replace(updateRequest.getIdCompany(), updateRequest);
						response = new HandlerResponseBean(200, gson.toJson(updateRequest, CompanyBean.class));

					} else {
						response = new HandlerResponseBean(400,
								String.format("{ error : \"Wrong Request: Mandatory parameters missing : %s\"}",
										getFieldsOnError(validate)));
					}
				}
			} else {
				response = new HandlerResponseBean(400, "{ error : \"Wrong Request: Company does not exist\"}");
			}
		} catch (NumberFormatException e) {
			response = new HandlerResponseBean(400,
					"{ error : \"Wrong Request: idCompany must be a numerical value\"}");
		}
		return response;
	}

}
