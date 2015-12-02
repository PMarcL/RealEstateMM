package org.RealEstateMM.domain.user;

public interface UserInformationsValidator {

	public void sendValidationRequest(UserInformations userInfos);

	public void confirmUserInformation(String confirmationCodeValue, UserRepository users);
}
