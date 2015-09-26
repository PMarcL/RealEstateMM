package org.RealEstateMM.authentication.session;

import java.util.Date;

import org.RealEstateMM.services.roles.RightManager;

public class Session {
	public final String pseudonym;
	public final String token;
	public final RightManager role;
	public final Date creationDate;

	public Session(String pseudonym, String token, RightManager role) {
		this.pseudonym = pseudonym;
		this.token = token;
		this.role = role;
		this.creationDate = new Date();
	}

}