package org.RealEstateMM.services.DTO;

public class UserInformationsDTO {
	private String name;
	private String email;
	private String phoneNumber;
	private String pseudonym;

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String newEmail) {
		email = newEmail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String newPhoneNumber) {
		phoneNumber = newPhoneNumber;
	}

	public String getPseudonym() {
		return pseudonym;
	}

	public void setPseudonym(String newPseudo) {
		pseudonym = newPseudo;
	}
}
