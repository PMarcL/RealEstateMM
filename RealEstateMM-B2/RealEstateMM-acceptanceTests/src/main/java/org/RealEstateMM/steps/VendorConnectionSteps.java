package org.RealEstateMM.steps;

import static org.junit.Assert.*;

import org.RealEstateMM.services.UserConnectionService;
import org.RealEstateMM.services.DTO.UserCredentials;
import org.RealEstateMM.services.DTO.UserInformationsDTO;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class VendorConnectionSteps {

	private UserConnectionService connectionService;
	private UserInformationsDTO returnedUser;

	@Given("an anonymous user")
	public void givenAnAnonymousUser() {

	}

	@When("he enters valid vendor credentials")
	public void whenHeEntersValidVendorCredentials() {
		UserCredentials credentials = new UserCredentials();
		connectionService = new UserConnectionService();
		returnedUser = connectionService.connectWithCredentials(credentials);
	}

	@Then("he is connected to his vendor account")
	public void thenHeIsConnectedToHisVendorAccount() {
		assertNotNull(returnedUser);
	}
}
