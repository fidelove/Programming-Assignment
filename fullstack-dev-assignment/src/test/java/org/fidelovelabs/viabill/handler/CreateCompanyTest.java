package org.fidelovelabs.viabill.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.fidelovelabs.viabill.mock.IdCompanyMock;
import org.fidelovelabs.viabill.model.CompanyBean;
import org.fidelovelabs.viabill.model.HandlerResponseBean;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.hazelcast.core.IAtomicLong;

public class CreateCompanyTest {

	Map<Long, CompanyBean> mapCompanies;
	CreateCompanyRequestHandler requestHandler;
	Gson gson = new Gson();

	@Before
	public void init() {
		mapCompanies = new HashMap<Long, CompanyBean>();
		IAtomicLong idCompany = EasyMock.createMock(IAtomicLong.class);
		EasyMock.expect(idCompany.getAndIncrement()).andStubDelegateTo(new IdCompanyMock());
		EasyMock.replay(idCompany);
		requestHandler = new CreateCompanyRequestHandler(mapCompanies, idCompany);
	}

	@Test
	public void createCompanyOk() {

		CompanyBean newCompany = new CompanyBean("companyName", "companyAddress", "companyCity", "companyCountry",
				"mail@company.com", "20-21-22-23", Arrays.asList("owner1", "owner2"));
		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), gson.toJson(newCompany));
		assertEquals("The creation of the company has failed", 200, response.getStatus());
		assertNotNull("The company must be created and it wasn't", response.getBody());

		CompanyBean companyBean = mapCompanies.get(Long.valueOf(0));

		assertNotNull("The company must be created and it wasn't", companyBean);
		assertEquals("The company name must be companyName and it isn't", companyBean.getName(), "companyName");
	}

	@Test
	public void createCompanyMissingMandatoryInformation() {

		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(),
				gson.toJson(new CompanyBean()));
		assertEquals("The new company couldn't be created beacause mandatory information was missing", 400,
				response.getStatus());
		assertNotNull("The new company couldn't be created beacause mandatory information was missing",
				response.getBody());

		assertTrue("No companies should be stored", mapCompanies.isEmpty());
	}

	@Test
	public void createCompanyWrongEmailFormat() {

		CompanyBean newCompany = new CompanyBean("companyName", "companyAddress", "companyCity", "companyCountry",
				"mail@company", "companyPhoneNumber", Arrays.asList("owner1", "owner2"));
		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), gson.toJson(newCompany));
		assertEquals("The new company couldn't be created beacause email format was incorrect", 400,
				response.getStatus());
		assertNotNull("The new company couldn't be created beacause email format was incorrect", response.getBody());

		assertTrue("No companies should be stored", mapCompanies.isEmpty());
	}

	@Test
	public void createCompanyEmptyOptionalFields() {

		CompanyBean newCompany = new CompanyBean("companyName", "companyAddress", "companyCity", "companyCountry", "",
				"", Arrays.asList("owner1"));
		HandlerResponseBean response = requestHandler.handle(new HashMap<String, String>(), gson.toJson(newCompany));
		assertEquals("The new company must be created", 200, response.getStatus());
		assertNotNull("The new company must be created", response.getBody());

		CompanyBean companyBean = mapCompanies.get(Long.valueOf(0));

		assertNotNull("The company must be created and it wasn't", companyBean);
		assertEquals("The company name must be companyName and it isn't", companyBean.getName(), "companyName");

	}
}