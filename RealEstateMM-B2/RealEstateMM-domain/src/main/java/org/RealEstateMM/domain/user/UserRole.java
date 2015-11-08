package org.RealEstateMM.domain.user;

public abstract class UserRole {
	public enum AccessLevel {
		SELLER, BUYER, ADMIN
	}

	private AccessLevel accessLevel;

	public UserRole(AccessLevel description) {
		this.accessLevel = description;
	}

	public AccessLevel getRoleDescription() {
		return accessLevel;
	}

	public boolean isAuthorized(AccessLevel requiredAccessLevel) {
		return accessLevel == requiredAccessLevel;
	}

}
