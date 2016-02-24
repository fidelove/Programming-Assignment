package org.fidelovelabs.assignment.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class BasicCompanyBean implements Serializable, Comparable<BasicCompanyBean> {

	private static final long serialVersionUID = 8080727923661840629L;

	protected long idCompany;
	@NotEmpty
	protected String name;

	public BasicCompanyBean() {
		super();
	}

	public BasicCompanyBean(long idCompany, String name) {
		super();
		this.idCompany = idCompany;
		this.name = name;
	}

	@Override
	public int compareTo(BasicCompanyBean o) {
		return (int) (this.idCompany - o.idCompany);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idCompany ^ (idCompany >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		BasicCompanyBean other = (BasicCompanyBean) obj;
		if (idCompany != other.idCompany)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("BasicCompanyBean [idCompany=%s, name=%s]", idCompany, name);

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
}