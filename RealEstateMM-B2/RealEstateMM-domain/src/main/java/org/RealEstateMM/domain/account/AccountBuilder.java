package org.RealEstateMM.domain.account;

import org.RealEstateMM.domain.user.User;

public class AccountBuilder {

	private User owner;

	public AccountBuilder withOwner(User user) {
		owner = user;
		return this;
	}

	public Account build() {
		return new Account(owner);
	}

}
