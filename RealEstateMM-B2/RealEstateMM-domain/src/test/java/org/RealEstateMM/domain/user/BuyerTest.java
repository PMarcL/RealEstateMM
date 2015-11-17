package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.Buyer;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.junit.Test;

public class BuyerTest {

	@Test
	public void hasBuyerAsRoleDescription() {
		Buyer buyer = new Buyer();
		assertEquals(AccessLevel.BUYER, buyer.getRoleDescription());
	}
}
