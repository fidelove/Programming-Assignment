package org.fidelovelabs.assignment.handler;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.IAtomicLong;

public class CreateCompanyRequestHandler extends AbstractRequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateCompanyRequestHandler.class.getName());

	protected IAtomicLong idCompany;

	public CreateCompanyRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super(mapCompanies);
	}

	public CreateCompanyRequestHandler(Map<Long, CompanyBean> mapCompanies, IAtomicLong idCompany) {
		super(mapCompanies);
		this.idCompany = idCompany;
	}

	@Override
	protected HandlerResponseBean handle(Map<String, String> map, String body) {

		logInfo(LOGGER, "New CreateCompany request");

		HandlerResponseBean response;

		CompanyBean newCompany = fromJson(body, CompanyBean.class);

		if (newCompany != null) {

			Set<ConstraintViolation<CompanyBean>> validate = validator.validate(newCompany);

			if (validate.isEmpty() && isEmailValid(newCompany.getEmail())
					&& isPhoneValid(newCompany.getPhoneNumber())) {

				newCompany.setIdCompany(idCompany.getAndIncrement());
				mapCompanies.put(newCompany.getIdCompany(), newCompany);

				logInfo(LOGGER, "New CreateCompany succesfully created");
				response = new HandlerResponseBean(200,
						String.format("{ \"idCompany\" : \"%d\"}", newCompany.getIdCompany()));

			} else {
				if (!isEmailValid(newCompany.getEmail())) {
					logError(LOGGER, "Wrong Request: Invalid Email Address");
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid Email Address\"}");

				} else if (!isPhoneValid(newCompany.getPhoneNumber())) {
					logError(LOGGER, "Wrong Request: Invalid phone number");
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid phone number\"}");

				} else {
					String fieldsOnError = getFieldsOnError(validate);

					logError(LOGGER, String.format("Wrong Request: Mandatory parameters missing %s", fieldsOnError));
					response = new HandlerResponseBean(400, String.format(
							"{ \"error\" : \"Wrong Request: Mandatory parameters missing : %s\"}", fieldsOnError));
				}
			}
		} else {
			logError(LOGGER, "Wrong Request: Request is not a valid JSON");
			response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Request is not a valid JSON\"}");
		}

		logInfo(LOGGER, "New CreateCompany request finished");
		return response;
	}
}