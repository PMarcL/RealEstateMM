package org.RealEstateMM.domain.models.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.RealEstateMM.domain.models.user.informations.Email;
import org.RealEstateMM.domain.models.user.informations.Name;
import org.RealEstateMM.domain.models.user.informations.PhoneNumber;
import org.RealEstateMM.testdata.DefaultUserBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
	private User user;
	private User otherUser;

	@Before
	public void initialisation() {
		user = new DefaultUserBuilder().build();
	}

	@Test
	public void givenTwoIdenticalUserInformationsWhenComparingThenShouldReturnsTrue() {
		otherUser = new DefaultUserBuilder().build();
		assertEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentEmailWhenComparingShouldReturnFalse() {
		final Email ANOTHER_EMAIL = new Email("emailTest@gmail.com");
		otherUser = new DefaultUserBuilder().withEmail(ANOTHER_EMAIL).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentNameWhenComparingShouldReturnFalse() {
		final Name ANOTHER_NAME = new Name("Bobby", "Dick");
		otherUser = new DefaultUserBuilder().withName(ANOTHER_NAME).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPhoneNumberWhenComparingShouldReturnFalse() {
		final PhoneNumber ANOTHER_PHONE_NUMBER = new PhoneNumber("(418)356-1234");
		otherUser = new DefaultUserBuilder().withPhoneNumber(ANOTHER_PHONE_NUMBER).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenTwoUserInformationsWithDifferentPseudonymWhenComparingShouldReturnFalse() {
		final String ANOTHER_PSEUDO = "jimmy129";
		otherUser = new DefaultUserBuilder().withPseudonym(ANOTHER_PSEUDO).build();
		assertNotEquals(user, otherUser);
	}

	@Test
	public void givenANullObjectWhenComparingThenShouldReturnsFalse() {
		assertNotEquals(user, null);
	}

	@Test
	public void givenAnObjectOfADifferentTypeWhenComparingThenShouldReturnsFalse() {
		String objectOfAnotherType = "I am a string";
		assertNotEquals(user, objectOfAnotherType);
	}

}
