package org.RealEstateMM.domain.session;

import org.RealEstateMM.domain.user.User;

public class Session {
	private User user;
	private String token;

	public Session(User user, String token) {
		this.user = user;
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}

}