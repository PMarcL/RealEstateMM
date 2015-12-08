package org.RealEstateMM.persistence.xml.user;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.domain.user.UserRole;
import org.RealEstateMM.domain.user.UserRole.AccessLevel;
import org.RealEstateMM.domain.user.UserRoleFactory;
import org.RealEstateMM.persistence.xml.util.DateUtil;
import org.junit.Before;
import org.junit.Test;

public class XmlUserAssemblerTest {
	private XmlUserAssembler assembler;
	private XmlUser xmlUser;
	private User user;
	private UserRoleFactory roleFactory;

	@Before
	public void setup() {
		roleFactory = mock(UserRoleFactory.class);

		assembler = new XmlUserAssembler(roleFactory);
		user = aUser().build();
		createXmlUser();
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserShouldHaveSamePseudonym() {
		XmlUser result = assembler.fromUser(user);
		assertEquals(user.getPseudonym(), result.getPseudonym());
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserShouldHaveSameUserInformations() {
		XmlUser result = assembler.fromUser(user);

		UserInformations userInfos = user.getUserInformations();
		assertEquals(userInfos.password, result.getPassword());
		assertEquals(userInfos.firstName, result.getFirstName());
		assertEquals(userInfos.lastName, result.getLastName());
		assertEquals(userInfos.emailAddress, result.getEmail());
		assertEquals(userInfos.phoneNumber, result.getPhoneNumber());
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserShouldHaveRoleDescritionToString() {
		XmlUser result = assembler.fromUser(user);
		assertEquals(user.getRoleDescription().toString(), result.getUserType());
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserShouldHaveSameLockState() {
		XmlUser result = assembler.fromUser(user);
		assertEquals(user.isLocked(), result.getLocked());
	}

	@Test
	public void givenAXmlUserWhenAssemblinguserFromXmlUserThenUserShouldHaveSamePseudonym() {
		User result = assembler.toUser(xmlUser);
		assertEquals(xmlUser.getPseudonym(), result.getPseudonym());
	}

	@Test
	public void givenAXmlUserWhenAssemblingUserFromXmlUserThenUserHasIdenticalFields() {
		User result = assembler.toUser(xmlUser);

		UserInformations userInfo = result.getUserInformations();
		assertEquals(xmlUser.getEmail(), userInfo.emailAddress);
		assertEquals(xmlUser.getFirstName(), userInfo.firstName);
		assertEquals(xmlUser.getLastName(), userInfo.lastName);
		assertEquals(xmlUser.getPassword(), userInfo.password);
		assertEquals(xmlUser.getPhoneNumber(), userInfo.phoneNumber);
	}

	@Test
	public void givenAXmlUserWhenAssemblingUserFromXmlUserThenUserShouldHaveUserRoleFromFactory() {
		UserRole role = mock(UserRole.class);
		given(role.getRoleDescription()).willReturn(AccessLevel.ADMIN);
		given(roleFactory.create(xmlUser.getUserType())).willReturn(role);

		User result = assembler.toUser(xmlUser);

		assertEquals(AccessLevel.ADMIN, result.getRoleDescription());
	}

	@Test
	public void givenLockedXmlUserWhenAssemblingUserFromXmlUserThenUserShouldBeLocked() {
		User result = assembler.toUser(xmlUser);
		assertTrue(result.isLocked());
	}

	@Test
	public void givenUnlockedXmlUserWhenAssemblingUserFromXmlUserThenUserShouldBeUnlocked() {
		xmlUser.setLocked(false);
		User result = assembler.toUser(xmlUser);
		assertFalse(result.isLocked());
	}

	private UserBuilder aUser() {
		return new UserBuilder();
	}

	private void createXmlUser() {
		xmlUser = new XmlUser();
		xmlUser.setEmail(UserBuilder.DEFAULT_EMAIL_ADDRESS);
		xmlUser.setFirstName(UserBuilder.DEFAULT_FIRST_NAME);
		xmlUser.setLastName(UserBuilder.DEFAULT_LAST_NAME);
		xmlUser.setPassword(UserBuilder.DEFAULT_PASSWORD);
		xmlUser.setPhoneNumber(UserBuilder.DEFAULT_PHONE_NUMBER);
		xmlUser.setPseudonym(UserBuilder.DEFAULT_PSEUDONYM);
		xmlUser.setUserType(UserBuilder.DEFAULT_USER_ROLE.toString());
		xmlUser.setLocked(UserBuilder.DEFAULT_LOCK_STATE);
		xmlUser.setLastLoginDate(DateUtil.formatDate(UserBuilder.DEFAULT_LAST_LOGIN_DATE));
	}
}
