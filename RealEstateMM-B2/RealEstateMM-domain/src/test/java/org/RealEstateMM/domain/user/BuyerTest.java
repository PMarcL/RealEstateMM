package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserRole.RoleDescription;
import org.junit.Test;

public class BuyerTest {

	@Test
	public void hasBuyerAsRoleDescription() {
		Buyer buyer = new Buyer();
		assertEquals(RoleDescription.BUYER, buyer.getRoleDescription());
	}
}
