package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserRole.RoleDescription;
import org.junit.Before;
import org.junit.Test;

public class UserRoleFactoryTest {
	private final String SELLER_VALUE = RoleDescription.SELLER.toString();
	private final String BUYER_VALUE = RoleDescription.BUYER.toString();
	private final String ADMIN_VALUE = RoleDescription.ADMIN.toString();

	private UserRoleFactory factory;

	@Before
	public void setup() {
		factory = new UserRoleFactory();
	}

	@Test
	public void givenValueIndicatesSellerWhenCreateShouldReturnSeller() {
		UserRole result = factory.create(SELLER_VALUE);
		assertTrue(result instanceof Seller);
	}

	@Test
	public void givenValueIndicatesBuyerWhenCreateShouldReturnBuyer() {
		UserRole result = factory.create(BUYER_VALUE);
		assertTrue(result instanceof Buyer);
	}

	@Test
	public void givenValueIndicatesAdminWhenCreateShouldReturnAdmin() {
		UserRole result = factory.create(ADMIN_VALUE);
		assertTrue(result instanceof Administrator);
	}

	@Test(expected = InvalidRoleDescriptionException.class)
	public void givenInvalidValueWhenCreateShouldThrowException() {
		factory.create("invalidRoleDescription");
	}
}
