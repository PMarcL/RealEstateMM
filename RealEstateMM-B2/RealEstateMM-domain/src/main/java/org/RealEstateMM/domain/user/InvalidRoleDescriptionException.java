package org.RealEstateMM.domain.user;

public class InvalidRoleDescriptionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRoleDescriptionException(String roleDescription) {
		super(String.format("%s is not a valid user role description.", roleDescription));
	}
}
