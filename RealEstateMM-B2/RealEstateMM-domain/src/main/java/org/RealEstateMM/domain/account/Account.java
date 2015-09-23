package org.RealEstateMM.domain.account;

import org.RealEstateMM.domain.user.User;

public class Account {

	private User owner;

	public Account(User owner) {
		this.owner = owner;
	}

	public User getOwner() {
		return owner;
	}

}
