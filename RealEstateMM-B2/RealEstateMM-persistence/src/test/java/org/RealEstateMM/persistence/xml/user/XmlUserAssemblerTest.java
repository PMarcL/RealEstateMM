package org.RealEstateMM.persistence.xml.user;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.junit.Before;
import org.junit.Test;

public class XmlUserAssemblerTest {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(XmlUserAssembler.DATE_FORMAT_NOW);

	private XmlUserAssembler assembler;
	private XmlUser xmlUser;

	@Before
	public void setup() {
		assembler = new XmlUserAssembler();
		createXmlUser();
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserHasIdenticalFields() {
		XmlUser result = assembler.fromUser(aUser().build());

		assertEquals(UserBuilder.DEFAULT_PSEUDONYM, result.getPseudonym());
		assertEquals(UserBuilder.DEFAULT_PASSWORD, result.getPassword());
		assertEquals(UserBuilder.DEFAULT_FIRST_NAME, result.getFirstName());
		assertEquals(UserBuilder.DEFAULT_LAST_NAME, result.getLastName());
		assertEquals(UserBuilder.DEFAULT_EMAIL_ADDRESS, result.getEmail());
		assertEquals(UserBuilder.DEFAULT_PHONE_NUMBER, result.getPhoneNumber());
		assertEquals(UserBuilder.DEFAULT_USER_TYPE_DESC, result.getUserType());
		assertEquals(UserBuilder.DEFAULT_LOCK_STATE, result.getLocked());
		assertEquals(dateFormatter.format(UserBuilder.DEFAULT_LAST_LOGIN_DATE), result.getLastLoginDate());
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
		assertEquals(xmlUser.getPseudonym(), userInfo.pseudonym);
		assertEquals(xmlUser.getUserType(), result.getUserTypeDescription());
		assertEquals(xmlUser.getLastLoginDate(), dateFormatter.format(result.getLastLoginDate()));
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
		xmlUser.setUserType(UserBuilder.DEFAULT_USER_TYPE_DESC);
		xmlUser.setLocked(UserBuilder.DEFAULT_LOCK_STATE);
		xmlUser.setLastLoginDate(dateFormatter.format(UserBuilder.DEFAULT_LAST_LOGIN_DATE));
	}
}
