package org.fidelovelabs.assignment.handler;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fidelovelabs.assignment.handler.GetCompaniesRequestHandler;
import org.fidelovelabs.assignment.model.BasicCompanyBean;
import org.fidelovelabs.assignment.model.CompanyBean;
import org.fidelovelabs.assignment.model.HandlerResponseBean;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GetCompaniesTest {

	Map<Long, CompanyBean> mapCompanies;
	GetCompaniesRequestHandler requestHandler;
	Gson gson = new Gson();

	@Before
	public void init() {
		mapCompanies = new HashMap<Long, CompanyBean>();
		requestHandler = new GetCompaniesRequestHandler(mapCompanies);
	}

	@Test
	public void getEmptyCompanies() {

		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), "");

		assertEquals("No companies should be returned", 200, response.getStatus());

		Type companyBeanType = new TypeToken<ArrayList<BasicCompanyBean>>() {
		}.getType();
		List<CompanyBean> companies = gson.fromJson(response.getBody(), companyBeanType);
		assertEquals("No companies should be returned", 0, companies.size());
	}

	@Test
	public void getOneCompany() {

		CompanyBean companyBean = new CompanyBean(0, "name", "address", "city", "country", "email", "phoneNumber",
				Arrays.asList("owner1", "owner2"));
		mapCompanies.put(Long.valueOf(0), companyBean);

		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), "");

		assertEquals("Request should be correct", 200, response.getStatus());

		Type companyBeanType = new TypeToken<ArrayList<BasicCompanyBean>>() {
		}.getType();
		List<CompanyBean> companies = gson.fromJson(response.getBody(), companyBeanType);
		assertEquals("Only company should be returned", 1, companies.size());
	}

	@Test
	public void getSeveralCompany() {

		for (int i = 0; i < 5; i++) {
			CompanyBean companyBean = new CompanyBean(i, "name", "address", "city", "country", "email", "phoneNumber",
					Arrays.asList("owner1", "owner2"));
			mapCompanies.put(Long.valueOf(i), companyBean);
		}

		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), "");

		assertEquals("Request should be correct", 200, response.getStatus());

		Type companyBeanType = new TypeToken<ArrayList<BasicCompanyBean>>() {
		}.getType();
		List<CompanyBean> companies = gson.fromJson(response.getBody(), companyBeanType);
		assertEquals("5 companies should be returned", 5, companies.size());
	}
}