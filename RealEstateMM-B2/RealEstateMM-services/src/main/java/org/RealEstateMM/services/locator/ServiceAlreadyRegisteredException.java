package org.RealEstateMM.services.locator;

@SuppressWarnings("serial")
public class ServiceAlreadyRegisteredException extends RuntimeException {

	public ServiceAlreadyRegisteredException(String className) {
		super("The implementation for this service " + className + " was already registered.");
	}

}
