package org.RealEstateMM.authentication.session;

import java.util.Date;

import org.RealEstateMM.services.roles.Role;

public class Session {
	public final String pseudonym;
	public final String token;
	public final Role role;
	public final Date creationDate;

	public Session(String pseudonym, String token) {
		this.pseudonym = pseudonym;
		this.token = token;
		this.role = null;
		this.creationDate = new Date();
	}

}