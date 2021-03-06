package org.RealEstateMM.services.property;

import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.user.UserAuthorizations;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.services.property.dtos.PropertyDTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class PropertyServiceSecurityTest {
	private final String PSEUDONYM = "bobby134";
	private PropertyDTO propertyDTO;
	private UserAuthorizations authorizations;
	private PropertyServiceHandler serviceHandler;
	private PropertyServiceSecurity service;

	@Before
	public void setup() {
		propertyDTO = mock(PropertyDTO.class);
		authorizations = mock(UserAuthorizations.class);
		serviceHandler = mock(PropertyServiceHandler.class);

		service = new PropertyServiceSecurity(serviceHandler, authorizations);
	}

	@Test
	public void givenAPseudonymAndPropertyDTOWhenUploadPropertyShouldValidateIfUserIsAuthorizedBeforeUploadingPropery()
			throws Throwable {
		service.uploadProperty(PSEUDONYM, propertyDTO);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.SELLER);
		inOrder.verify(serviceHandler).uploadProperty(PSEUDONYM, propertyDTO);
	}

	@Test
	public void givenPseudonymAndPropertyDTOWhenEditPropertyFeaturesShouldValidateIfUserIsAuthorizedBeforeEditingProperty()
			throws Throwable {
		service.editPropertyFeatures(PSEUDONYM, propertyDTO);

		InOrder inOrder = inOrder(authorizations, serviceHandler);
		inOrder.verify(authorizations).validateUserAuthorizations(PSEUDONYM, AccessLevel.SELLER);
		inOrder.verify(serviceHandler).editPropertyFeatures(PSEUDONYM, propertyDTO);
	}
}
