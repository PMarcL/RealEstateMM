package org.RealEstateMM.restapi.responses;

public class LoginResponse {

	private String userType;
	private String token;

	public LoginResponse() {
	}

	public LoginResponse(String userType, String token) {
		this.userType = userType;
		this.token = token;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean equals(LoginResponse other) {
		return (this.token.equals(other.token)) && (this.userType.equals(other.userType));
	}
}
