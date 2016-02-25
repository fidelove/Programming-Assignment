package org.fidelovelabs.assignment.handler;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.collections.CollectionUtils;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateCompanyDetailsRequestHandler extends AbstractRequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateCompanyDetailsRequestHandler.class.getName());

	public UpdateCompanyDetailsRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		logInfo(LOGGER, "Update Company Details request");

		HandlerResponseBean response;

		try {

			CompanyBean companyBean = getCompanyById(map);

			if (companyBean != null) {

				CompanyBean updateRequest = fromJson(body, CompanyBean.class);

				if (updateRequest == null) {
					logError(LOGGER, "Wrong Request: Request is not a valid JSON");
					response = new HandlerResponseBean(400,
							"{ \"error\" : \"Wrong Request: Request is not a valid JSON\"}");

				} else if (CollectionUtils.isNotEmpty(updateRequest.getBeneficiaOwner())) {
					logError(LOGGER, "Wrong Request: In order to update beneficial owners use addOwner");
					response = new HandlerResponseBean(400,
							"{ \"error\" : \"Wrong Request: In order to update beneficial owners use addOwner\"}");

				} else if (!isEmailValid(updateRequest.getEmail())) {
					logError(LOGGER, "Wrong Request: Invalid Email Address");
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid Email Address\"}");

				} else if (!isPhoneValid(updateRequest.getPhoneNumber())) {
					logError(LOGGER, "Wrong Request: Invalid phone number");
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid phone number\"}");

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
					updateRequest.setPhoneNumber(updateRequest.getPhoneNumber() != null ? updateRequest.getPhoneNumber()
							: companyBean.getPhoneNumber());
					updateRequest.setBeneficiaOwner(companyBean.getBeneficiaOwner());

					Set<ConstraintViolation<CompanyBean>> validate = validator.validate(updateRequest);

					if (validate.isEmpty()) {
						logInfo(LOGGER, "Update Company Details request succeed");
						mapCompanies.replace(updateRequest.getIdCompany(), updateRequest);
						response = new HandlerResponseBean(200, gson.toJson(updateRequest, CompanyBean.class));

					} else {
						String fieldsOnError = getFieldsOnError(validate);

						logError(LOGGER,
								String.format("Wrong Request: Mandatory parameters missing %s", fieldsOnError));
						response = new HandlerResponseBean(400, String.format(
								"{ \"error\" : \"Wrong Request: Mandatory parameters missing : %s\"}", fieldsOnError));
					}
				}
			} else {
				logError(LOGGER, "Wrong Request: Company does not exist");
				response = new HandlerResponseBean(400, "{ error : \"Wrong Request: Company does not exist\"}");
			}
		} catch (NumberFormatException e) {
			logError(LOGGER, "Wrong Request: idCompany must be a numerical value");
			response = new HandlerResponseBean(400,
					"{ error : \"Wrong Request: idCompany must be a numerical value\"}");
		}

		logInfo(LOGGER, "Update Company Details request finished");
		return response;
	}

}
