package org.fidelovelabs.assignment.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.fidelovelabs.assignment.handler.GetCompanyDetailsRequestHandler;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class GetCompanyDetailsTest {

	Map<Long, CompanyBean> mapCompanies;
	GetCompanyDetailsRequestHandler requestHandler;
	Gson gson = new Gson();

	@Before
	public void init() {
		mapCompanies = new HashMap<Long, CompanyBean>();
		requestHandler = new GetCompanyDetailsRequestHandler(mapCompanies);
	}

	@Test
	public void getExistingCompanyDetails() {

		CompanyBean companyBean = new CompanyBean(123, "name", "address", "city", "country", "email", "phoneNumber",
				Arrays.asList("owner1", "owner2"));
		mapCompanies.put(Long.valueOf(123), companyBean);

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		HandlerResponseBean response = requestHandler.handle(map, "");

		assertEquals("The request should have succeed", 200, response.getStatus());

		CompanyBean requestedCompany = gson.fromJson(response.getBody(), CompanyBean.class);
		assertEquals("The company details are not the same", requestedCompany, companyBean);
	}

	@Test
	public void getUnexistingCompanyDetails() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		HandlerResponseBean response = requestHandler.handle(map, "");

		assertEquals("The company should not exist", 400, response.getStatus());
		assertNotNull("The responde should contain an error message", response.getBody());
	}
}
