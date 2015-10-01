package org.RealEstateMM.persistence.xml.user;

import static org.junit.Assert.*;

import org.RealEstateMM.domain.helpers.UserBuilder;
import org.RealEstateMM.domain.user.User;
import org.RealEstateMM.domain.user.UserInformations;
import org.RealEstateMM.persistence.xml.user.XmlUser;
import org.RealEstateMM.persistence.xml.user.XmlUserAssembler;
import org.junit.Before;
import org.junit.Test;

public class XmlUserAssemblerTest {

	private XmlUserAssembler assembler;
	private XmlUser xmlUser;

	@Before
	public void setup() {
		assembler = new XmlUserAssembler();
		createXmlUser();
	}

	@Test
	public void givenAUserWhenAssemblingXmlUserFromUserThenXmlUserHasIdenticalFields() {
		XmlUser result = assembler.fromUser(new UserBuilder().build());

		assertEquals(UserBuilder.DEFAULT_PSEUDO, result.getPseudonym());
		assertEquals(UserBuilder.DEFAULT_PASSWORD, result.getPassword());
		assertEquals(UserBuilder.DEFAULT_FIRST_NAME, result.getFirstName());
		assertEquals(UserBuilder.DEFAULT_LAST_NAME, result.getLastName());
		assertEquals(UserBuilder.DEFAULT_EMAIL, result.getEmail());
		assertEquals(UserBuilder.DEFAULT_PHONE_NUMBER, result.getPhoneNumber());
		assertEquals(UserBuilder.DEFAULT_USER_TYPE_DESC, result.getUserType());
	}

	@Test
	public void givenAXmlUserWhenAssemblingUserFromXmlUserThenUserHasIdenticalFields() {
		User result = assembler.toUser(xmlUser);
		UserInformations userInfo = result.getUserInformations();

		assertEquals(xmlUser.getEmail(), userInfo.email);
		assertEquals(xmlUser.getFirstName(), userInfo.firstName);
		assertEquals(xmlUser.getLastName(), userInfo.lastName);
		assertEquals(xmlUser.getPassword(), userInfo.password);
		assertEquals(xmlUser.getPhoneNumber(), userInfo.phoneNumber);
		assertEquals(xmlUser.getPseudonym(), userInfo.pseudonym);
		assertEquals(xmlUser.getUserType(), result.getUserTypeDescription());
	}

	private void createXmlUser() {
		xmlUser = new XmlUser();
		xmlUser.setEmail(UserBuilder.DEFAULT_EMAIL);
		xmlUser.setFirstName(UserBuilder.DEFAULT_FIRST_NAME);
		xmlUser.setLastName(UserBuilder.DEFAULT_LAST_NAME);
		xmlUser.setPassword(UserBuilder.DEFAULT_PASSWORD);
		xmlUser.setPhoneNumber(UserBuilder.DEFAULT_PHONE_NUMBER);
		xmlUser.setPseudonym(UserBuilder.DEFAULT_PSEUDO);
		xmlUser.setUserType(UserBuilder.DEFAULT_USER_TYPE_DESC);
	}
}
