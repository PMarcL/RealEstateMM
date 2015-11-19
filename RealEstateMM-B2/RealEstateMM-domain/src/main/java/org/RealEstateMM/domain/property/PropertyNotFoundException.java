package org.RealEstateMM.domain.property;

public class PropertyNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public PropertyNotFoundException() {
		super("No property found at given address.");
	}

}