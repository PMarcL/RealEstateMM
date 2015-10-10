package org.RealEstateMM.jersey.requestDTO;

public class EmailConfirmationDTO {

	private String confirmationCode;

	public EmailConfirmationDTO(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

	public String getConfirmationCode() {
		return confirmationCode;
	}

	public void setConfirmationCode(String confirmationCode) {
		this.confirmationCode = confirmationCode;
	}

}
