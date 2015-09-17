package org.RealEstateMM.services.dto;

public class UserCredentials {

	private String password;
	private String pseudonym;

	public String getPassword() {
		return password;
	}

	public String getPseudo() {
		return pseudonym;
	}

	public void setPassword(String userPassword) {
		password = userPassword;
	}

	public void setPseudo(String userPseudo) {
		pseudonym = userPseudo;
	}

}
