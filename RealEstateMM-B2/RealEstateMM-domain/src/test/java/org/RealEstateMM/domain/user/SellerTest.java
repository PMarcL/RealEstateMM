package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.Seller;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Test;

public class SellerTest {

	@Test
	public void hasSellerAsRoleDescription() {
		Seller seller = new Seller();
		assertEquals(AccessLevel.SELLER, seller.getRoleDescription());
	}
}
