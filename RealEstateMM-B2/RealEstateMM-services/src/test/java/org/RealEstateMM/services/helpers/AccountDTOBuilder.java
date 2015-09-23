package org.RealEstateMM.services.helpers;

import org.RealEstateMM.services.dtos.account.AccountDTO;
import org.RealEstateMM.services.dtos.user.UserDTO;

public class AccountDTOBuilder {

	private UserDTO owner;

	public AccountDTOBuilder withOwner(UserDTO userDTO) {
		owner = userDTO;
		return this;
	}

	public AccountDTO build() {
		return new AccountDTO(owner);
	}

}
