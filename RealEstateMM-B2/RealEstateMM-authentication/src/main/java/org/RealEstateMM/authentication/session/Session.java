package org.RealEstateMM.authentication.session;

public class Session {
	public final String pseudonym;
	public final String token;

	public Session(String pseudonym, String token) {
		this.pseudonym = pseudonym;
		this.token = token;
	}

}