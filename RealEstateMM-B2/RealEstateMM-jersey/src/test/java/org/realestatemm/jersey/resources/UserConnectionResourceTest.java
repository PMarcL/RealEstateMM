package org.realestatemm.jersey.resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.RealEstateMM.jersey.resources.UserConnectionResource;
import org.RealEstateMM.services.dto.UserDTO;
import org.junit.Before;
import org.junit.Test;

public class UserConnectionResourceTest {
	
	private static final String FIRST_NAME = "bob";
	private static final String LAST_NAME = "sponge";
	private static final String PHONE_NUMBER = "418-666-6666";
	private static final String EMAIL = "bobcool@example.com";
	private static final String PSEUDONYM = "coolbob";
	
	private UserConnectionResource userConnectionResource;
	
	@Before
	public void Setup()
	{
		userConnectionResource = new UserConnectionResource();
	}

	@Test
	public void GivenAValidUserWhenAUserIsRegisteredThenTheServerShouldReturnResponseStatusOK() {
		UserDTO userDTO = new UserDTO();
		initializeValidDTO(userDTO);
		
		StatusType statusType = userConnectionResource.registerUser(userDTO).getStatusInfo();
		
		assertEquals(Status.OK, statusType);
	}

	@Test
	public void givenNoUserInfoWhenAUserIsRegisteredTheServerShouldReturnReponseStatusBadRequest()
	{
		UserDTO userDTO = new UserDTO();
		
		StatusType statusType = userConnectionResource.registerUser(userDTO).getStatusInfo();
		
		assertEquals(Status.BAD_REQUEST, statusType);
	}
	
	
	private void initializeValidDTO(UserDTO userDTO)
	{
		userDTO.setFirstName(FIRST_NAME);
		userDTO.setLastName(LAST_NAME);
		userDTO.setPhoneNumber(PHONE_NUMBER);
		userDTO.setPseudonym(PSEUDONYM);
		userDTO.setEmail(EMAIL);
	}
	
	

}
