package org.springframework.samples.petclinic.service;

public class LoggedMethod {
	public String className;
	public String methodName;
	public String response;

	public LoggedMethod(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
