package org.fidelovelabs.viabill.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

public class CompanyBean implements Serializable {

	private static final long serialVersionUID = 8080727923661840629L;

	private long idCompany;
	@NotEmpty
	private String name;
	@NotEmpty
	private String address;
	@NotEmpty
	private String city;
	@NotEmpty
	private String country;
	private String email;
	private String phoneNumber;
	@NotEmpty
	private List<String> beneficiaOwner;

	public CompanyBean() {
		super();
	}

	public CompanyBean(String name, String address, String city, String country, String email, String phoneNumber,
			List<String> beneficiaOwner) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.beneficiaOwner = beneficiaOwner;
	}

	public CompanyBean(long idCompany, String name, String address, String city, String country, String email,
			String phoneNumber, List<String> beneficiaOwner) {
		super();
		this.idCompany = idCompany;
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.beneficiaOwner = beneficiaOwner;
	}

	public long getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(long idCompany) {
		this.idCompany = idCompany;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getBeneficiaOwner() {
		if (beneficiaOwner == null) {
			beneficiaOwner = new ArrayList<>();
		}
		return beneficiaOwner;
	}

	public void setBeneficiaOwner(List<String> beneficiaOwner) {
		this.beneficiaOwner = beneficiaOwner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((beneficiaOwner == null) ? 0 : beneficiaOwner.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (int) (idCompany ^ (idCompany >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyBean other = (CompanyBean) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (beneficiaOwner == null) {
			if (other.beneficiaOwner != null)
				return false;
		} else if (!beneficiaOwner.equals(other.beneficiaOwner))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idCompany != other.idCompany)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{idCompany=" + idCompany + ", name=" + name + ", address=" + address + ", city=" + city + ", country="
				+ country + ", email=" + email + ", phoneNumber=" + phoneNumber + ", beneficiaOwner=" + beneficiaOwner
				+ "}";
	}
}