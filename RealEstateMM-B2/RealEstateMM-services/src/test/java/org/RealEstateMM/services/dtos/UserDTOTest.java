package org.RealEstateMM.services.dtos;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UserDTOTest {

	@Test
	public void equalsTest() {
		// To avoid error, please set Equals and HashCode methods final (like
		// this to avoid inheritance problem)
		EqualsVerifier.forClass(UserDTO.class).suppress(Warning.NONFINAL_FIELDS).verify();
	}

}
