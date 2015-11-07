package org.RealEstateMM.domain.user;

public class UserRoleFactory {

	public UserRole create(String roleDescription) {
		switch (roleDescription) {
		case "SELLER":
			return new Seller();
		case "BUYER":
			return new Buyer();
		case "ADMIN":
			return new Administrator();
		default:
			throw new InvalidRoleDescriptionException(roleDescription);
		}
	}
}
