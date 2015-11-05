package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserRole.RoleDescription;
import org.junit.Test;

public class SellerTest {

	@Test
	public void hasSellerAsRoleDescription() {
		Seller seller = new Seller();
		assertEquals(RoleDescription.SELLER, seller.getRoleDescription());
	}
}
