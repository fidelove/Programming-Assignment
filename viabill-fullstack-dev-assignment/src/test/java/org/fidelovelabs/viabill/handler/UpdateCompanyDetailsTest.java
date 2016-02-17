package org.fidelovelabs.viabill.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class UpdateCompanyDetailsTest {

	Map<Long, CompanyBean> mapCompanies;
	UpdateCompanyDetailsRequestHandler requestHandler;
	Gson gson = new Gson();

	@Before
	public void init() {
		mapCompanies = new HashMap<Long, CompanyBean>();
		requestHandler = new UpdateCompanyDetailsRequestHandler(mapCompanies);

		CompanyBean companyBean = new CompanyBean(123, "name", "address", "city", "country", "email@mail.com",
				"20-21-22-23", Arrays.asList("owner1", "owner2"));
		mapCompanies.put(Long.valueOf(123), companyBean);
	}

	@Test
	public void updateCompanyDetailsOk() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		CompanyBean updateCompany = new CompanyBean();
		updateCompany.setName("newNameCompany");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(updateCompany));

		assertEquals("The request should have succeed", 200, response.getStatus());

		CompanyBean updatedCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The company name should be updated", "newNameCompany", updatedCompany.getName());
	}

	@Test
	public void updateCompanyDetailsUnexistingCompany() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "1234");
		CompanyBean updateCompany = new CompanyBean();
		updateCompany.setName("newNameCompany");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(updateCompany));

		assertEquals("The request can not succeed", 400, response.getStatus());
		assertNotNull("The request should return an error", response.getBody());

		CompanyBean updatedCompany = mapCompanies.get(Long.valueOf(1234));
		assertNull("No company should exist with this idCompany", updatedCompany);
	}

	@Test
	public void updateCompanyErasingMandatoryFields() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		CompanyBean updateCompany = new CompanyBean();
		updateCompany.setName("");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(updateCompany));

		assertEquals("The request can not succeed", 400, response.getStatus());
		assertNotNull("The request should return an error", response.getBody());

		CompanyBean updatedCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The company name should keep the same", "name", updatedCompany.getName());
	}

	@Test
	public void updateCompanyWrongEmailAddress() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		CompanyBean updateCompany = new CompanyBean();
		updateCompany.setEmail("wrongEmail");
		;
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(updateCompany));

		assertEquals("The request can not succeed", 400, response.getStatus());
		assertNotNull("The request should return an error", response.getBody());

		CompanyBean updatedCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The company email should keep the same", "email@mail.com", updatedCompany.getEmail());
	}

}
