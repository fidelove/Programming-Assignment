package org.fidelovelabs.viabill.handler;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.validator.routines.EmailValidator;
import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.StringUtils;

public abstract class AbstractRequestHandler implements Route {

	protected Map<Long, CompanyBean> mapCompanies;
	protected Validator validator;
	protected Gson gson = new Gson();
	protected EmailValidator emailValidator = EmailValidator.getInstance();
	protected PhoneNumberUtil phoneValidator = PhoneNumberUtil.getInstance();

	/**
	 * Construcotr, which initialises the bean validator
	 * 
	 * @param mapCompanies
	 *            {@link java.util.Map} containing database information
	 */
	public AbstractRequestHandler(Map<Long, CompanyBean> mapCompanies) {
		super();
		this.mapCompanies = mapCompanies;

		Configuration<?> config = Validation.byDefaultProvider().configure();
		ValidatorFactory factory = config.buildValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 
	 * Abstract method to be overriden for each response handler. It will be
	 * invoked to process each user request
	 * 
	 * @param map
	 *            {@link java.util.Map} containing the route parameters in the
	 *            HTTP request
	 * @ @param
	 *       body {@link java.lang.String} JSON formatted string containing the
	 *       body of the HTTP request
	 * @return {@link org.fidelovelabs.viabill.model.HandlerResponseBean} Object
	 *         containing the HTTP response code and body
	 */
	protected abstract HandlerResponseBean handle(Map<String, String> map, String body);

	@Override
	public Object handle(Request request, Response response) throws Exception {

		if (isJSONValid(request.body())) {

			HandlerResponseBean handlerResponse = handle(request.params(), request.body());
			response.status(handlerResponse.getStatus());
			response.body(handlerResponse.getBody());
			return handlerResponse.getBody();

		} else {
			return "{ error : \"Request does not contain a valid JSON\"}";
		}

	}

	/**
	 * @param jsonString
	 * @return
	 */
	protected boolean isJSONValid(String jsonString) {
		try {
			gson.fromJson(jsonString, Object.class);
			return true;
		} catch (JsonSyntaxException ex) {
			return false;
		}
	}

	protected <T> T fromJson(String json, Type typeOfT) {
		try {
			return gson.fromJson(json, typeOfT);
		} catch (JsonParseException e) {
			return null;
		}
	}

	/**
	 * Returns the {@link org.fidelovelabs.viabill.model.CompanyBean} linked by
	 * the parameter <i>:idCompany</i> stored in the {@link java.util.Map}, or
	 * null if no company exists with this idCompany
	 * 
	 * @param map
	 *            {@link java.util.Map} containing the route parameters in the
	 *            HTTP request
	 * @return The companyBean
	 */
	protected CompanyBean getCompanyById(Map<String, String> map) {

		try {
			Long idCompany = Long.parseLong(map.get(":idcompany"));
			return mapCompanies.get(idCompany);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Method that checks if the email address is correct
	 * 
	 * @param email
	 *            {@link java.util.String} containing the email address to be
	 *            checked
	 * @return <i>true</i> if the parameter is a valid email address,
	 *         <i>false</i> otherwise
	 */
	protected boolean isEmailValid(String email) {
		return StringUtils.isEmpty(email) || emailValidator.isValid(email);
	}

	/**
	 * Method that checks if the phone number is correct. If it is not in the
	 * international format, it is assumed that it is an danish number
	 * 
	 * @param email
	 *            {@link java.util.String} containing the phone number to be
	 *            checked
	 * @return <i>true</i> if the parameter is a valid phone number,
	 *         <i>false</i> otherwise
	 */
	protected boolean isPhoneValid(String phoneNumber) {

		boolean isPhoneValid = true;

		if (StringUtils.isNotEmpty(phoneNumber)) {
			try {
				phoneValidator.parse(phoneNumber, "DE");
			} catch (NumberParseException e) {
				isPhoneValid = false;
			}
		}

		return isPhoneValid;
	}

	/**
	 * Method that build an string containing all the mandatory fields that are
	 * not filled
	 * 
	 * @param violations
	 *            {@link java.util.Set} containing the violations in the
	 *            validation process
	 * @return A string containing the mandatory fields that are not filled
	 */
	protected String getFieldsOnError(Set<ConstraintViolation<CompanyBean>> violations) {
		StringBuilder errorFields = new StringBuilder();
		violations.forEach((violation) -> errorFields.append(violation.getPropertyPath()).append(", "));
		return errorFields.substring(0, errorFields.length() - 2);
	}
}