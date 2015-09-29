package org.RealEstateMM.domain.user.usertype;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UserTypeFactoryTest {

	private UserTypeFactory factory;

	@Before
	public void setup() {
		factory = new UserTypeFactory();
	}

	@Test
	public void givenASellerDescriptionWhenBuildingUserTypeThenReturnsSellerType() {
		UserType result = factory.makeUserType(UserTypeDescription.SELLER);
		assertEquals(UserType.SELLER, result);
	}

	@Test
	public void givenABuyerDescriptionWhenBuildingUserTypeThenReturnsBuyerType() {
		UserType result = factory.makeUserType(UserTypeDescription.BUYER);
		assertEquals(UserType.BUYER, result);
	}

	@Test
	public void givenAnAdminDescriptionWhenBuildingUserTypeThenReturnsAdminType() {
		UserType result = factory.makeUserType(UserTypeDescription.ADMIN);
		assertEquals(UserType.ADMIN, result);
	}
}
