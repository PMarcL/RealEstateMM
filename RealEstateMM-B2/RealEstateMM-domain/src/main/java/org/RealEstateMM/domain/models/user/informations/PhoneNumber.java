package org.RealEstateMM.domain.models.user.informations;

public class PhoneNumber {
	private String value;

	public PhoneNumber(String phoneNumber) {
		this.value = phoneNumber;
		removeUnsupportedCharacters();

		if (hasNotTheRightSize())
			throw new PhoneNumberFormatException(phoneNumber);
	}

	private void removeUnsupportedCharacters() {
		final String EMPTY_CHARACTER = "";
		final String[] UNSUPPORTED_CHARACTERS = { ")", "(", "-", " " };

		for (String character : UNSUPPORTED_CHARACTERS)
			value = value.replace(character, EMPTY_CHARACTER);
	}

	private boolean hasNotTheRightSize() {
		final int DIGITS_NUMBER = 10;
		return value.length() != DIGITS_NUMBER;
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof PhoneNumber))
			return false;

		PhoneNumber phoneNumber = (PhoneNumber) object;
		return (this.value.equals(phoneNumber.value));
	}
}
