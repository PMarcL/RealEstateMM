package org.RealEstateMM.services.dtos.account;

import org.RealEstateMM.services.dtos.user.UserDTO;

public class AccountDTO {

	private UserDTO owner;
	private String type;

	public AccountDTO(UserDTO owner) {
		this.owner = owner;
	}

	public UserDTO getOwner() {
		return owner;
	}

	public void setOwner(UserDTO owner) {
		this.owner = owner;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
