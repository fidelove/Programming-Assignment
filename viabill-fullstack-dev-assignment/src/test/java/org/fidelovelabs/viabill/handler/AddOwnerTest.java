package org.fidelovelabs.viabill.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

public class AddOwnerTest {

	Map<Long, CompanyBean> mapCompanies;
	AddOwnerRequestHandler requestHandler;
	Gson gson = new Gson();

	@Before
	public void init() {
		mapCompanies = new HashMap<Long, CompanyBean>();
		requestHandler = new AddOwnerRequestHandler(mapCompanies);

		CompanyBean companyBean = new CompanyBean(123, "name", "address", "city", "country", "email@mail.com",
				"20-21-22-23", Arrays.asList("owner1"));
		mapCompanies.put(Long.valueOf(123), companyBean);
	}

	@Test
	public void addOwnerOk() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		List<String> newOwner = Arrays.asList("owner2");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(newOwner));

		assertEquals("The request should have succeed", 200, response.getStatus());

		CompanyBean updatedCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The number of owners should be 2", 2, updatedCompany.getBeneficiaOwner().size());
		assertEquals("The second owner should be owner2", "owner2", updatedCompany.getBeneficiaOwner().get(1));
	}

	@Test
	public void addOwnerUnexistingCompany() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "1234");
		List<String> newOwner = Arrays.asList("owner");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(newOwner));

		assertEquals("The request could not succeed", 400, response.getStatus());

		CompanyBean unexistingCompany = mapCompanies.get(Long.valueOf(1234));
		assertNull("No company should exist with this idCompany", unexistingCompany);

		CompanyBean existingCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The only one existing company should have only one owner", 1,
				existingCompany.getBeneficiaOwner().size());
		assertEquals("The only one existing company should have only one owner", "owner1",
				existingCompany.getBeneficiaOwner().get(0));
	}

	@Test
	public void addDuplicatedOwner() {

		Map<String, String> map = new HashMap<String, String>();
		map.put(":idcompany", "123");
		List<String> newOwner = Arrays.asList("owner1");
		HandlerResponseBean response = requestHandler.handle(map, gson.toJson(newOwner));

		assertEquals("The request could not succeed", 400, response.getStatus());

		CompanyBean existingCompany = mapCompanies.get(Long.valueOf(123));
		assertEquals("The company should have only one owner", 1, existingCompany.getBeneficiaOwner().size());
		assertEquals("The company should have only one owner", "owner1", existingCompany.getBeneficiaOwner().get(0));
	}
}