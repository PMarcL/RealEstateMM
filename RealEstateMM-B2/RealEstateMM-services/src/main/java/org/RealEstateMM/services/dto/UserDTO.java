package org.RealEstateMM.services.dto;

public class UserDTO {

	public String pseudonym;
	public String firstName;
	public String lastName;
	public String email;
	public String phoneNumber;

	public UserDTO() {
	}

	public UserDTO(String pseudonym, String firstName, String lastName, String email, String phoneNumber) {
		this.pseudonym = pseudonym;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

}
