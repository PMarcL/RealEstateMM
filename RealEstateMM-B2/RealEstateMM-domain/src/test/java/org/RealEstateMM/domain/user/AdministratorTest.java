package org.RealEstateMM.domain.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.user.UserRole.RoleDescription;
import org.junit.Test;

public class AdministratorTest {

	@Test
	public void hasAdminAsRoleDescription() {
		Administrator admin = new Administrator();
		assertEquals(RoleDescription.ADMIN, admin.getRoleDescription());
	}
}
