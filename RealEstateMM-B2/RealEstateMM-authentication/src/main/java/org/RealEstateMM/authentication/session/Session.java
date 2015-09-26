package org.RealEstateMM.authentication.session;

import java.util.Date;

public class Session {
	public final String pseudonym;
	public final String token;
	public final Date creationDate;

	public Session(String pseudonym, String token) {
		this.pseudonym = pseudonym;
		this.token = token;
		this.creationDate = new Date();
	}

}