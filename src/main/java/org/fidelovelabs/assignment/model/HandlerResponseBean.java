package org.fidelovelabs.assignment.model;

public class HandlerResponseBean {

	private int status;
	private String body;

	public HandlerResponseBean() {
		super();
	}

	public HandlerResponseBean(int status, String body) {
		super();
		this.status = status;
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}