package org.fidelovelabs.assignment.handler;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;

import com.hazelcast.core.IAtomicLong;

public class CreateCompanyRequestHandler extends AbstractRequestHandler {

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

		HandlerResponseBean response;

		CompanyBean newCompany = fromJson(body, CompanyBean.class);

		if (newCompany != null) {

			Set<ConstraintViolation<CompanyBean>> validate = validator.validate(newCompany);

			if (validate.isEmpty() && isEmailValid(newCompany.getEmail())
					&& isPhoneValid(newCompany.getPhoneNumber())) {

				newCompany.setIdCompany(idCompany.getAndIncrement());
				mapCompanies.put(newCompany.getIdCompany(), newCompany);

				response = new HandlerResponseBean(200,
						String.format("{ \"idCompany\" : \"%d\"}", newCompany.getIdCompany()));

			} else {
				if (!isEmailValid(newCompany.getEmail())) {
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid Email Address\"}");

				} else if (!isPhoneValid(newCompany.getPhoneNumber())) {
					response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Invalid phone number\"}");

				} else {
					response = new HandlerResponseBean(400,
							String.format("{ \"error\" : \"Wrong Request: Mandatory parameters missing : %s\"}",
									getFieldsOnError(validate)));
				}
			}
		} else {
			response = new HandlerResponseBean(400, "{ \"error\" : \"Wrong Request: Request is not a valid JSON\"}");
		}

		return response;
	}
}