package org.RealEstateMM.domain.user.informations;

public class Name {
	public final String firstName;
	public final String lastName;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Name))
			return false;

		Name otherName = (Name) object;
		return (this.firstName.equals(otherName.firstName) && this.lastName.equals(otherName.lastName));
	}

}
