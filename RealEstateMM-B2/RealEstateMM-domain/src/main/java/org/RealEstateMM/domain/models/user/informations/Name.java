package org.RealEstateMM.domain.models.user.informations;

public class Name {

	public final String firstName;
	public final String lastName;
	public final String fullName;

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = firstName + " " + lastName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Name)) {
			return false;
		} else {
			Name name = (Name) obj;
			boolean areEquals = false;
			areEquals = this.firstName.equals(name.firstName);
			areEquals = this.lastName.equals(name.lastName);
			areEquals = this.fullName.equals(name.fullName);
			return areEquals;
		}
	}

}
