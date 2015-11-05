package org.RealEstateMM.domain.user;

public abstract class UserRole {
	public enum RoleDescription {
		SELLER, BUYER, ADMIN
	}

	private RoleDescription description;

	public UserRole(RoleDescription description) {
		this.description = description;
	}

	public RoleDescription getRoleDescription() {
		return description;
	}

}
