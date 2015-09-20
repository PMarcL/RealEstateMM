package org.RealEstateMM.domain.user.informations;

import static org.junit.Assert.*;

import org.junit.Test;

public class NameTest {

	@Test
	public void givenTwoIdenticalNamesWhenComparingShouldBeEquals() {
		Name aName = new Name("John", "Doe");
		assertTrue(aName.equals(aName));
	}

	@Test
	public void givenObjectOfAnotherTypeWhenComparingShouldNotBeEquals() {
		Name aName = new Name("John", "Doe");
		String objectOfAnotherType = "";
		assertFalse(aName.equals(objectOfAnotherType));
	}
}
